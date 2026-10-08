(function () {
  'use strict';

  /* Menu recolhível no celular ------------------------------------------ */

  var toggle = document.querySelector('.nav-toggle');
  var nav = document.querySelector('.site-nav');

  function setMenu(open) {
    toggle.setAttribute('aria-expanded', String(open));
    toggle.querySelector('.visually-hidden').textContent = open ? 'Fechar menu' : 'Abrir menu';
    nav.classList.toggle('is-open', open);
    document.body.classList.toggle('menu-open', open);
  }

  if (toggle && nav) {
    toggle.addEventListener('click', function () {
      setMenu(toggle.getAttribute('aria-expanded') !== 'true');
    });

    nav.addEventListener('click', function (event) {
      if (event.target.closest('a')) setMenu(false);
    });

    document.addEventListener('keydown', function (event) {
      if (event.key === 'Escape' && nav.classList.contains('is-open')) {
        setMenu(false);
        toggle.focus();
      }
    });
  }

  /* Destaque do link da seção visível ----------------------------------- */

  var links = document.querySelectorAll('.nav-list a[href^="#"]');

  if ('IntersectionObserver' in window && links.length) {
    var linkById = {};
    links.forEach(function (link) {
      linkById[link.getAttribute('href').slice(1)] = link;
    });

    var observer = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        var link = linkById[entry.target.id];
        if (!link || !entry.isIntersecting) return;
        links.forEach(function (l) { l.classList.remove('is-active'); });
        link.classList.add('is-active');
      });
    }, { rootMargin: '-45% 0px -50% 0px' });

    Object.keys(linkById).forEach(function (id) {
      var section = document.getElementById(id);
      if (section) observer.observe(section);
    });
  }

  /* Informações profissionais e de contato vindas da API ---------------- */
  // Campos ainda não informados pela cliente chegam nulos e mantêm o
  // placeholder do HTML. Sem o backend (ex.: arquivo aberto direto no
  // navegador), a página continua exibindo os placeholders.

  function fill(el, text) {
    el.textContent = text;
    el.classList.remove('placeholder');
  }

  function renderAddress(location) {
    var cityState = [location.city, location.state].filter(Boolean).join(' / ');
    var lines = {
      street: location.street,
      complement: location.complement,
      region: [location.district, cityState].filter(Boolean).join(' — '),
      postalCode: location.postalCode ? 'CEP ' + location.postalCode : ''
    };
    var hasAddress = Object.keys(lines).some(function (key) { return lines[key]; });

    if (hasAddress) {
      document.querySelectorAll('.address [data-location]').forEach(function (el) {
        var value = lines[el.getAttribute('data-location')];
        if (value) fill(el, value);
        else el.remove();
      });
    }

    var accessInfo = document.querySelector('[data-location="accessInfo"]');
    if (accessInfo && location.accessInfo) fill(accessInfo, location.accessInfo);
  }

  // Só aceita os formatos de link gerados pela API
  var CONTACT_URL = { whatsapp: /^https:\/\/wa\.me\/\d+$/, email: /^mailto:[^\s]+$/ };

  function renderContacts(contact) {
    document.querySelectorAll('[data-contact]').forEach(function (el) {
      var type = el.getAttribute('data-contact');
      var channel = contact[type];
      if (!channel || !CONTACT_URL[type] || !CONTACT_URL[type].test(channel.url)) return;

      var link = document.createElement('a');
      link.className = 'contact-value';
      link.href = channel.url;
      link.textContent = channel.display;
      if (type === 'whatsapp') {
        link.target = '_blank';
        link.rel = 'noopener noreferrer';
        link.setAttribute('aria-label', 'Conversar pelo WhatsApp: ' + channel.display + ' (abre em nova aba)');
      }
      el.replaceWith(link);
    });
  }

  function renderProfile(profile) {
    document.querySelectorAll('[data-profile]').forEach(function (el) {
      var value = profile[el.getAttribute('data-profile')];
      if (typeof value === 'string' && value) fill(el, value);
    });
    if (profile.location) renderAddress(profile.location);
    if (profile.contact) renderContacts(profile.contact);
  }

  if (window.fetch && window.location.protocol !== 'file:') {
    fetch('api/public/profile', { headers: { Accept: 'application/json' } })
      .then(function (response) { return response.ok ? response.json() : null; })
      .then(function (profile) { if (profile) renderProfile(profile); })
      .catch(function () { /* backend indisponível: mantém os placeholders */ });
  }
})();
