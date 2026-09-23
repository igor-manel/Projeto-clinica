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

  /* Assistente automático (demonstração, sem integração) ---------------- */
  /* Respostas pré-definidas; nada é enviado nem salvo fora desta página.   */

  // Trechos marcados com ph() aparecem como conteúdo provisório.
  function ph(text) {
    return { placeholder: text };
  }

  var CHANNELS = ['WhatsApp', 'Telefone', 'E-mail'];

  var TOPICS = [
    {
      label: 'Quero saber sobre atendimento presencial',
      reply: [
        ['Sobre o atendimento presencial: ', ph('[informações a confirmar com a profissional]'), '.'],
        ['O endereço do consultório fica na seção Localização: ', ph('[endereço a informar]'), '.']
      ],
      link: { href: '#localizacao', label: 'Ver localização' }
    },
    {
      label: 'Quero saber sobre atendimento online',
      reply: [
        ['O atendimento online acontece por videochamada, em horário combinado previamente com a profissional.'],
        ['Plataforma e condições: ', ph('[a definir com a profissional]'), '.']
      ],
      link: { href: '#online', label: 'Saber mais sobre o atendimento online' }
    },
    {
      label: 'Quero consultar disponibilidade',
      reply: [
        ['Na seção Horários você pode consultar dias e horários de exemplo. Nesta demonstração, os horários são fictícios.'],
        ['O site não reserva horários: a confirmação depende do contato direto com a profissional.']
      ],
      link: { href: '#disponibilidade', label: 'Ver horários' }
    }
  ];

  var chat = document.querySelector('.chat');
  if (chat) initChat(chat);

  function initChat(root) {
    var log = root.querySelector('.chat-log');
    var actions = root.querySelector('.chat-actions');
    var state;
    var turnStart;

    function addMessage(author, lines, link) {
      var msg = document.createElement('div');
      msg.className = 'chat-msg chat-msg-' + author;

      var who = document.createElement('span');
      who.className = 'visually-hidden';
      who.textContent = author === 'bot' ? 'Assistente: ' : 'Você: ';
      msg.appendChild(who);

      lines.forEach(function (parts) {
        var p = document.createElement('p');
        [].concat(parts).forEach(function (part) {
          if (part && part.placeholder) {
            var span = document.createElement('span');
            span.className = 'placeholder';
            span.textContent = part.placeholder;
            p.appendChild(span);
          } else {
            p.appendChild(document.createTextNode(part));
          }
        });
        msg.appendChild(p);
      });

      if (link) {
        var a = document.createElement('a');
        a.className = 'chat-link';
        a.href = link.href;
        a.textContent = link.label;
        msg.appendChild(a);
      }

      log.appendChild(msg);
      if (author === 'user' || !turnStart) turnStart = msg;
      // Mantém visível o início da rodada atual (pergunta + resposta).
      log.scrollTop = turnStart.offsetTop - 12;
    }

    function bot(lines, link) {
      addMessage('bot', lines, link);
    }

    function user(text) {
      addMessage('user', [text]);
    }

    function focusFirstAction() {
      var first = actions.querySelector('input, button');
      if (first) first.focus();
    }

    function setActions(items, focus) {
      actions.textContent = '';
      items.forEach(function (item) {
        var button = document.createElement('button');
        button.type = 'button';
        button.className = 'chat-option' + (item.secondary ? ' chat-option-secondary' : '');
        button.textContent = item.label;
        button.addEventListener('click', item.onSelect);
        actions.appendChild(button);
      });
      if (focus !== false) focusFirstAction();
    }

    // Opção que repete a escolha como mensagem da pessoa e segue o fluxo.
    function option(label, next) {
      return {
        label: label,
        onSelect: function () {
          user(label);
          next();
        }
      };
    }

    function start(focus) {
      state = { name: '', channel: '' };
      turnStart = null;
      log.textContent = '';
      bot([
        ['Olá! Sou o assistente automático de ', ph('[Nome da profissional]'), '.'],
        ['Minhas respostas são automáticas e pré-definidas. Este canal não é indicado para situações de urgência.']
      ]);
      setActions([option('Tenho interesse em uma consulta', askName)], focus);
    }

    function askName() {
      bot([['Que bom! Como podemos chamar você? O nome é opcional.']]);
      actions.textContent = '';

      var form = document.createElement('form');
      form.className = 'chat-form';
      form.noValidate = true;

      var field = document.createElement('div');
      field.className = 'chat-field';
      var label = document.createElement('label');
      label.htmlFor = 'chat-name';
      label.textContent = 'Seu nome (opcional)';
      var input = document.createElement('input');
      input.id = 'chat-name';
      input.type = 'text';
      input.maxLength = 40;
      input.autocomplete = 'given-name';
      input.setAttribute('aria-describedby', 'chat-name-hint');
      field.appendChild(label);
      field.appendChild(input);

      var submit = document.createElement('button');
      submit.type = 'submit';
      submit.className = 'chat-option';
      submit.textContent = 'Continuar';

      var skip = document.createElement('button');
      skip.type = 'button';
      skip.className = 'chat-option chat-option-secondary';
      skip.textContent = 'Pular';

      var hint = document.createElement('p');
      hint.id = 'chat-name-hint';
      hint.className = 'chat-hint';
      hint.textContent = 'Use apenas o primeiro nome. Não informe dados pessoais ou de saúde.';

      form.appendChild(field);
      form.appendChild(submit);
      form.appendChild(skip);
      form.appendChild(hint);
      actions.appendChild(form);

      function skipName() {
        user('Prefiro não informar');
        askChannel(['Sem problemas.']);
      }

      form.addEventListener('submit', function (event) {
        event.preventDefault();
        var name = input.value.trim();
        if (!name) return skipName();
        state.name = name;
        user(name);
        askChannel(['Prazer, ' + name + '!']);
      });
      skip.addEventListener('click', skipName);

      focusFirstAction();
    }

    function askChannel(intro) {
      bot([
        intro,
        ['Qual canal você prefere para o contato com a profissional? Aqui você só indica a preferência: nenhum número ou e-mail é solicitado.']
      ]);
      setActions(CHANNELS.map(function (channel) {
        return option(channel, function () {
          state.channel = channel;
          showMenu([['Anotado: preferência por ' + channel + '.'], ['Como posso ajudar?']]);
        });
      }));
    }

    function showMenu(lines) {
      bot(lines);
      var items = TOPICS.map(function (topic) {
        return option(topic.label, function () {
          bot(topic.reply, topic.link);
          showMenu([['Posso ajudar com mais alguma coisa?']]);
        });
      });
      items.push(option('Quero falar com a profissional', finish));
      setActions(items);
    }

    function finish() {
      bot([
        [
          (state.name ? 'Agradecemos o contato, ' + state.name + '!' : 'Agradecemos o contato!') +
          ' Você indicou preferência por ' + state.channel + '.'
        ],
        ['Para combinar a consulta, entre em contato pelos canais da seção Contato: ', ph('[contatos a informar]'), '.'],
        ['Lembrete: esta é uma demonstração. Nenhuma mensagem foi enviada, nenhum horário foi reservado e a confirmação da consulta depende do contato direto com a profissional.']
      ], { href: '#contato', label: 'Ver contatos' });
      setActions([
        option('Voltar às opções', function () {
          showMenu([['Claro. Como posso ajudar?']]);
        }),
        { label: 'Recomeçar conversa', secondary: true, onSelect: function () { start(); } }
      ]);
    }

    // Links do assistente: rola até a seção e move o foco para o título dela.
    log.addEventListener('click', function (event) {
      var link = event.target.closest('.chat-link');
      if (!link) return;
      var href = link.getAttribute('href');
      var section = document.querySelector(href);
      var heading = section && section.querySelector('h2');
      if (!heading) return;
      // A navegação padrão da âncora descartaria o foco; a rolagem é feita aqui.
      event.preventDefault();
      if (history.pushState) history.pushState(null, '', href);
      section.scrollIntoView();
      heading.setAttribute('tabindex', '-1');
      heading.focus({ preventScroll: true });
    });

    root.querySelector('.chat-restart').addEventListener('click', function () {
      start();
    });

    start(false);
  }
})();
