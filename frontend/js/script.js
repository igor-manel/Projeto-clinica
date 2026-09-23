(function () {
  'use strict';

  /* Menu recolhível no celular ------------------------------------------ */

  var toggle = document.querySelector('.nav-toggle');
  var nav = document.querySelector('.site-nav');

  function setMenu(open) {
    toggle.setAttribute('aria-expanded', String(open));
    toggle.querySelector('.visually-hidden').textContent = open ? 'Fechar menu' : 'Abrir menu';
    nav.classList.toggle('is-open', open);
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

  /* Disponibilidade (demonstração, sem agendamento) --------------------- */

  var slots = document.querySelectorAll('button.slot');
  var selectionText = document.querySelector('.selection-text');
  var selectionCta = document.querySelector('.selection-cta');

  slots.forEach(function (slot) {
    slot.setAttribute('aria-pressed', 'false');

    slot.addEventListener('click', function () {
      slots.forEach(function (s) {
        s.classList.remove('is-selected');
        s.setAttribute('aria-pressed', 'false');
      });
      slot.classList.add('is-selected');
      slot.setAttribute('aria-pressed', 'true');

      selectionText.textContent =
        'Você escolheu ' + slot.dataset.day + ' às ' + slot.dataset.time +
        '. Entre em contato para confirmar — o horário só é reservado após a combinação.';
      selectionCta.hidden = false;
    });
  });
})();
