/* ===============================================================
   FPS ARENA
   Lógica principal do site
================================================================ */


/* ===============================================================
   CONFIGURAÇÕES
================================================================ */

const API_URL = "http://localhost:8080";

let currentPage = "home";
let saldoOculto = false;


/* ===============================================================
   MENU
================================================================ */

const NAV_ITEMS = [
  { id: "home", label: "Início", icon: "home" },
  { id: "campeonatos", label: "Campeonatos", icon: "trophy" },
  { id: "partidas", label: "Partidas", icon: "swords" },
  { id: "rankings", label: "Rankings", icon: "bar-chart-3" },
  { id: "times", label: "Times", icon: "users" },
  { id: "loja", label: "Loja", icon: "shopping-bag" },
  { id: "carteira", label: "Carteira", icon: "wallet" },
  { id: "config", label: "Configurações", icon: "settings" },
];


const TITLES = {
  home: "Home",
  campeonatos: "Campeonatos",
  partidas: "Partidas",
  criar: "Criar partida",
  lobby: "Lobby da partida",
  rankings: "Rankings",
  times: "Times",
  loja: "Loja",
  carteira: "Carteira",
  config: "Configurações",
};


/* ===============================================================
   AVATARES
================================================================ */

const AVATAR_GRADIENTS = [
  "linear-gradient(135deg,#6366f1,#d946ef)",
  "linear-gradient(135deg,#0ea5e9,#4f46e5)",
  "linear-gradient(135deg,#f59e0b,#e11d48)",
  "linear-gradient(135deg,#10b981,#0d9488)",
  "linear-gradient(135deg,#d946ef,#db2777)",
  "linear-gradient(135deg,#f43f5e,#ea580c)",
];


function avatarGradient(name) {

  const code =
    name
      ? name.charCodeAt(0) +
        (name.charCodeAt(name.length - 1) || 0)
      : 0;

  return AVATAR_GRADIENTS[
    code % AVATAR_GRADIENTS.length
  ];
}


function avatarHtml(name, size) {

  const initials = name
    ? name.slice(0, 2).toUpperCase()
    : "?";

  return `
    <div
      class="avatar"
      style="
        width:${size}px;
        height:${size}px;
        background:${avatarGradient(name)};
        font-size:${size * 0.36}px;
      "
    >
      ${initials}
    </div>
  `;
}


/* ===============================================================
   NAVEGAÇÃO
================================================================ */

function goPage(id) {

  currentPage = id;


  /* Esconde todas as páginas */

  document
    .querySelectorAll(".page")
    .forEach((page) => {
      page.classList.remove("active");
    });


  /* Mostra a página escolhida */

  const target = document.querySelector(
    `.page[data-page="${id}"]`
  );

  if (target) {
    target.classList.add("active");
  }


  /* Atualiza título */

  const pageTitle =
    document.getElementById("page-title");

  if (pageTitle) {
    pageTitle.textContent =
      TITLES[id] ?? id;
  }


  /* Botão voltar */

  const backBtn =
    document.getElementById("back-btn");

  if (backBtn) {

    if (id === "criar" || id === "lobby") {

      backBtn.classList.remove("hidden");

    } else {

      backBtn.classList.add("hidden");

    }

    backBtn.onclick = () => goPage("partidas");
  }


  /* Menu ativo */

  document
    .querySelectorAll(".nav-btn")
    .forEach((btn) => {

      const navId = btn.dataset.nav;

      const active =
        navId === id ||
        (
          (id === "criar" || id === "lobby") &&
          navId === "partidas"
        );

      btn.classList.toggle(
        "active",
        active
      );

    });


  /* Recria ícones */

  lucide.createIcons();


  /* Carrega campeonatos quando abrir a página */

  if (id === "campeonatos") {
    carregarCampeonatos();
  }

}


/* ===============================================================
   SIDEBAR
================================================================ */

function renderSidebar() {

  const nav =
    document.getElementById("sidebar-nav");

  if (!nav) return;


  nav.innerHTML = NAV_ITEMS
    .map(
      (item) => `
        <div
          class="nav-btn"
          data-nav="${item.id}"
          onclick="goPage('${item.id}')"
        >

          <i
            data-lucide="${item.icon}"
            style="width:17px;height:17px;"
          ></i>

          <span>
            ${item.label}
          </span>

        </div>
      `
    )
    .join("");


  lucide.createIcons();
}


/* ===============================================================
   CRIAR PARTIDA
================================================================ */

function initCriarPartidaInteractions() {

  const modoGroup =
    document.getElementById("modo-group");

  const permGroup =
    document.getElementById("perm-group");


  if (modoGroup) {

    modoGroup.addEventListener(
      "click",
      (event) => {

        const button =
          event.target.closest(".modo-btn");

        if (!button) return;


        document
          .querySelectorAll(".modo-btn")
          .forEach((btn) => {

            btn.classList.remove(
              "selected"
            );

          });


        button.classList.add(
          "selected"
        );

      }
    );

  }


  if (permGroup) {

    permGroup.addEventListener(
      "click",
      (event) => {

        const button =
          event.target.closest(".perm-btn");

        if (!button) return;


        document
          .querySelectorAll(".perm-btn")
          .forEach((btn) => {

            btn.classList.remove(
              "selected"
            );

          });


        button.classList.add(
          "selected"
        );

      }
    );

  }

}


/* ===============================================================
   LOBBY
================================================================ */

const TIME_CT = [
  { name: "Nexa", tag: "LÍDER" },
  { name: "Lukzera" },
  { name: "dzt" },
  { name: "chelo" },
  { name: "kNg" },
];


const TIME_T = [
  { name: "mch", tag: "LÍDER" },
  { name: "trk" },
  { name: "honda" },
  { name: "venomz" },
  { name: null },
];


function renderTeam(
  containerId,
  players
) {

  const element =
    document.getElementById(containerId);

  if (!element) return;


  element.innerHTML =
    players
      .map(
        (player) => `

          <div
            class="
              flex
              items-center
              justify-between
              rounded-lg
              px-2
              py-2
              ${
                player.name
                  ? "bg-white/[0.03]"
                  : "border border-dashed border-white/10"
              }
            "
          >

            <div class="flex items-center gap-2">

              ${
                player.name
                  ? avatarHtml(
                      player.name,
                      28
                    )
                  : `
                    <div
                      class="
                        h-7
                        w-7
                        rounded-full
                        border
                        border-dashed
                        border-white/15
                      "
                    ></div>
                  `
              }


              <span
                class="
                  text-xs
                  ${
                    player.name
                      ? "font-medium text-white/80"
                      : "text-white/30"
                  }
                "
              >
                ${
                  player.name ??
                  "Aguardando jogador..."
                }
              </span>


              ${
                player.tag
                  ? `
                    <span
                      class="
                        rounded
                        bg-indigo-500/20
                        px-1.5
                        py-0.5
                        text-[9px]
                        font-bold
                        text-indigo-300
                      "
                    >
                      ${player.tag}
                    </span>
                  `
                  : ""
              }

            </div>


            ${
              player.name
                ? `
                  <span
                    class="
                      text-[10px]
                      font-semibold
                      text-emerald-400
                    "
                  >
                    PRONTO
                  </span>
                `
                : ""
            }

          </div>

        `
      )
      .join("");

}


/* ===============================================================
   CHAT
================================================================ */

function sendChat() {

  const input =
    document.getElementById("chat-input");

  if (!input) return;


  const message =
    input.value.trim();

  if (!message) return;


  const box =
    document.getElementById("chat-box");

  if (!box) return;


  const line =
    document.createElement("div");

  line.innerHTML = `
    <span class="font-semibold text-indigo-300">
      Nexa:
    </span>

    <span class="text-white/60">
      ${escaparHtml(message)}
    </span>
  `;


  box.appendChild(line);


  box.scrollTop =
    box.scrollHeight;


  input.value = "";

}


function initLobbyInteractions() {

  const input =
    document.getElementById("chat-input");

  if (!input) return;


  input.addEventListener(
    "keydown",
    (event) => {

      if (event.key === "Enter") {
        sendChat();
      }

    }
  );

}


/* ===============================================================
   CARTEIRA
================================================================ */

const TRANSACOES = [

  {
    label: "Depósito via PIX",
    date: "04/06/2024 - 14:30",
    value: "+ R$ 100,00",
    positive: true
  },

  {
    label: "Inscrição em campeonato",
    date: "04/06/2024 - 13:15",
    value: "- R$ 20,00",
    positive: false
  },

  {
    label: "Premiação - Night Battle",
    date: "03/06/2024 - 22:45",
    value: "+ R$ 150,00",
    positive: true
  },

  {
    label: "Saque via PIX",
    date: "03/06/2024 - 18:20",
    value: "- R$ 80,00",
    positive: false
  },

];


const METODOS = [

  {
    label: "PIX",
    icon: "wallet-2"
  },

  {
    label: "Cartão de Crédito",
    icon: "credit-card"
  },

  {
    label: "PayPal",
    icon: "banknote"
  },

  {
    label: "Transferência Bancária",
    icon: "landmark"
  },

];


function toggleSaldo() {

  saldoOculto =
    !saldoOculto;


  const saldo =
    document.getElementById("saldo-valor");

  if (!saldo) return;


  saldo.textContent =
    saldoOculto
      ? "R$ ••••••"
      : "R$ 250,00";

}


function renderCarteira() {

  const transacoes =
    document.getElementById(
      "transacoes-box"
    );

  if (transacoes) {

    transacoes.innerHTML =
      TRANSACOES
        .map(
          (transaction) => `

            <div
              class="
                flex
                items-center
                justify-between
                rounded-lg
                px-2
                py-3
                hover:bg-white/[0.02]
              "
            >

              <div
                class="
                  flex
                  items-center
                  gap-3
                "
              >

                <span
                  class="
                    h-2
                    w-2
                    rounded-full
                    ${
                      transaction.positive
                        ? "bg-emerald-400"
                        : "bg-rose-400"
                    }
                  "
                ></span>


                <div>

                  <div class="text-sm text-white/80">
                    ${transaction.label}
                  </div>

                  <div class="text-[11px] text-white/30">
                    ${transaction.date}
                  </div>

                </div>

              </div>


              <span
                class="
                  text-sm
                  font-semibold
                  ${
                    transaction.positive
                      ? "text-emerald-400"
                      : "text-rose-400"
                  }
                "
              >
                ${transaction.value}
              </span>

            </div>

          `
        )
        .join("");

  }


  const metodos =
    document.getElementById(
      "metodos-box"
    );

  if (metodos) {

    metodos.innerHTML =
      METODOS
        .map(
          (method) => `

            <div
              class="
                flex
                items-center
                justify-between
                py-2
              "
            >

              <span
                class="
                  flex
                  items-center
                  gap-2.5
                  text-sm
                  text-white/70
                "
              >

                <i
                  data-lucide="${method.icon}"
                  class="text-white/40"
                  style="width:15px;height:15px;"
                ></i>

                ${method.label}

              </span>


              <button
                class="
                  text-xs
                  font-medium
                  text-indigo-400
                  hover:underline
                "
              >
                Adicionar
              </button>

            </div>

          `
        )
        .join("");

  }


  lucide.createIcons();

}


/* ===============================================================
   CAMPEONATOS
   API SPRING BOOT
================================================================ */

function escaparHtml(texto) {

  const div =
    document.createElement("div");

  div.textContent =
    texto ?? "";

  return div.innerHTML;
}


function formatarMoeda(valor) {

  return Number(valor)
    .toLocaleString(
      "pt-BR",
      {
        style: "currency",
        currency: "BRL"
      }
    );

}


/* ===============================================================
   CARD DE CAMPEONATO
================================================================ */

function cardCampeonatoHtml(
  campeonato
) {

  const aoVivo =
    campeonato.status === "AO_VIVO"
      ? `
        <span class="live-badge">

          <span class="live-dot"></span>

          AO VIVO

        </span>
      `
      : "";


  const status =
    campeonato.status === "ABERTO"
      ? `
        <span class="status-badge open">
          ABERTO
        </span>
      `
      : `
        <span class="status-badge">
          ${escaparHtml(campeonato.status)}
        </span>
      `;


  return `

    <article class="tournament-card">

      <!-- IMAGEM -->

      <div class="tournament-cover">

        <div class="tournament-cover-overlay"></div>

        <div class="tournament-cover-title">
          FPS ARENA
        </div>

        ${aoVivo}

      </div>


      <!-- CONTEÚDO -->

      <div class="p-5">


        <div class="flex items-start justify-between gap-3">

          <div>

            <h4 class="text-lg font-bold">
              ${escaparHtml(campeonato.nome)}
            </h4>

            <p class="mt-1 text-xs text-white/40">
              ${escaparHtml(campeonato.modo)}
              •
              ${escaparHtml(campeonato.tipo)}
            </p>

          </div>

          ${status}

        </div>


        <div class="mt-5 flex items-end justify-between">


          <div>

            <div class="text-[10px] uppercase tracking-wider text-white/30">
              Premiação
            </div>

            <div class="mt-1 text-xl font-black text-emerald-400">
              ${formatarMoeda(campeonato.premiacao)}
            </div>

          </div>


          <div class="text-right">

            <div class="text-[10px] uppercase tracking-wider text-white/30">
              Jogadores
            </div>

            <div class="mt-1 flex items-center justify-end gap-1 text-sm font-semibold text-white/70">

              <i
                data-lucide="users"
                style="width:14px;height:14px;"
              ></i>

              ${campeonato.inscritos}/${campeonato.vagas}

            </div>

          </div>


        </div>


        <button
          onclick="abrirCampeonato(${campeonato.id})"
          class="tournament-button mt-5 w-full"
        >
          Ver campeonato

          <i
            data-lucide="arrow-right"
            style="width:14px;height:14px;"
          ></i>

        </button>


      </div>

    </article>

  `;

}


/* ===============================================================
   CARREGAR CAMPEONATOS
================================================================ */

async function carregarCampeonatos() {

  const homeBox =
    document.getElementById(
      "campeonatos-destaque"
    );

  const listaBox =
    document.getElementById(
      "campeonatos-lista"
    );


  try {

    const resposta =
      await fetch(
        `${API_URL}/campeonatos`
      );


    if (!resposta.ok) {
      throw new Error(
        `Erro HTTP ${resposta.status}`
      );
    }


    const campeonatos =
      await resposta.json();


    /* -----------------------------------------------------------
       HOME
    ----------------------------------------------------------- */

    if (homeBox) {

      if (
        !Array.isArray(campeonatos) ||
        campeonatos.length === 0
      ) {

        homeBox.innerHTML = `
          <div class="empty-tournament">

            <i
              data-lucide="trophy"
              style="width:25px;height:25px;"
            ></i>

            <p>
              Nenhum campeonato disponível no momento.
            </p>

          </div>
        `;

      } else {

        /* Mostra no máximo 3 na Home */

        homeBox.innerHTML =
          campeonatos
            .slice(0, 3)
            .map(cardCampeonatoHtml)
            .join("");

      }

    }


    /* -----------------------------------------------------------
       CAMPEONATOS
    ----------------------------------------------------------- */

    if (listaBox) {

      if (
        !Array.isArray(campeonatos) ||
        campeonatos.length === 0
      ) {

        listaBox.innerHTML = `
          <div class="empty-tournament">

            <i
              data-lucide="trophy"
              style="width:25px;height:25px;"
            ></i>

            <p>
              Nenhum campeonato disponível.
            </p>

          </div>
        `;

      } else {

        listaBox.innerHTML =
          campeonatos
            .map(cardCampeonatoHtml)
            .join("");

      }

    }


    /* -----------------------------------------------------------
       CAMPEONATO DO HERO
    ----------------------------------------------------------- */

    const heroCampeonato =
      document.getElementById(
        "hero-campeonato"
      );


    if (heroCampeonato) {

      if (
        Array.isArray(campeonatos) &&
        campeonatos.length > 0
      ) {

        heroCampeonato.textContent =
          campeonatos[0].nome;

      } else {

        heroCampeonato.textContent =
          "Nenhum disponível";

      }

    }


    /* Recria ícones */

    lucide.createIcons();


  } catch (erro) {

    console.error(
      "Erro ao carregar campeonatos:",
      erro
    );


    if (homeBox) {

      homeBox.innerHTML = `
        <div class="error-box">

          <i
            data-lucide="wifi-off"
            style="width:22px;height:22px;"
          ></i>

          <div>

            <p class="font-semibold">
              Não foi possível carregar os campeonatos.
            </p>

            <p class="mt-1 text-xs text-white/40">
              Verifique se o Spring Boot está rodando.
            </p>

          </div>

        </div>
      `;

    }


    if (listaBox) {

      listaBox.innerHTML = `
        <div class="error-box">

          <i
            data-lucide="wifi-off"
            style="width:22px;height:22px;"
          ></i>

          <div>

            <p class="font-semibold">
              API indisponível.
            </p>

            <p class="mt-1 text-xs text-white/40">
              Verifique se o backend está rodando em localhost:8080.
            </p>

          </div>

        </div>
      `;

    }


    lucide.createIcons();

  }

}


/* ===============================================================
   ABRIR CAMPEONATO
================================================================ */

function abrirCampeonato(id) {

  console.log(
    "Campeonato selecionado:",
    id
  );


  /*
    Por enquanto apenas mostramos no console.

    Quando criarmos a página de detalhes,
    podemos fazer:

    goPage("detalhes-campeonato");

  */

}


/* ===============================================================
   INICIALIZAÇÃO
================================================================ */

document.addEventListener(
  "DOMContentLoaded",
  () => {

    renderSidebar();

    renderTeam(
      "time-ct",
      TIME_CT
    );

    renderTeam(
      "time-t",
      TIME_T
    );

    renderCarteira();

    initCriarPartidaInteractions();

    initLobbyInteractions();

    carregarCampeonatos();

    goPage("home");

    lucide.createIcons();

  }
);