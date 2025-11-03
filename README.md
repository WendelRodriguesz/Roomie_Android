# Roomiê — Match de colegas de quarto e vagas em moradias compartilhadas

> App Android (Kotlin + Jetpack Compose) para conectar quem **tem vaga** em casa/apê com quem **procura dividir moradia** — com **match por compatibilidade**, **anúncios completos**, **filtros**, **chat** e **notificações**.

---

## 🔗 Acesso rápido

* 📲 **Protótipo (Figma):** [Abrir protótipo no Figma](https://www.figma.com/design/DGb7c0RmOmOCkEB8EFKvUK/ROOMI%C3%8A?node-id=0-1&t=z5ZfftR03nRm7SOH-1)
* 📄 **Documento de Requisitos (V1):** [Abrir Documento de Requisitos (V1)](https://docs.google.com/document/d/1xKwrXNIDa-8gP6puTsLHHXviSZ059ORM33PhJtQTdDY/edit?usp=drive_link)
* 🗓️ **Cronograma / Sprints (V1):** [Abrir Cronograma / Sprints (V1)](https://docs.google.com/spreadsheets/d/1PyaJ2X6koHX5hzBK8WGPBXVN5_KcumqvEyi_-TPyL_s/edit?usp=drive_link)
* 🗂️ **Drive do Projeto:** [Abrir pasta no Google Drive](https://drive.google.com/drive/u/1/folders/18dUYSGzleAyHZLVjhjfoBI5Kx5x08U2s)
* 🧭 **Notion (controle do projeto e processos):** [Abrir workspace no Notion](https://www.notion.so/HDC-Home-2714386361d28090a9f2ff1c101d4c72?source=copy_link)
* 💻 **Repositório (GitHub):** [Abrir Roomie_Android no GitHub](https://github.com/WendelRodriguesz/Roomie_Android)
* 📞 **Reuniões:** Quartas, 19:00 — [Entrar no Google Meet (sbv-vkqh-ut)](https://meet.google.com/sbv-vkqh-ut)

---

## 👥 Equipe

- **Anaildo do Nascimento Silva** – 552836 — anaildosilv@alu.ufc.br  
- **Camile Isidorio Araújo** – 555251 — camileisi@alu.ufc.br  
- **Debora Silva Viana** – 557337 — deboraviana@alu.ufc.br  
- **Francisco Werley da Silva** – 553948 — franciscowerley@alu.ufc.br  
- **Wendel Rodrigues Viana** – 548323 — wendel2010@alu.ufc.br  

---

## 🧭 Objetivo

Resolver a busca fragmentada por moradia compartilhada (grupos/classificados) com **match por perfil de convivência**, **transparência de custos**, **filtros finos** e **comunicação direta**.

---

## 🚀 Funcionalidades Principais (MVP)

> (✓ = concluído; vazio = a fazer. Em colchetes, **Sprint** do Cronograma V1)

- [ ] **Cadastro e Login** (A001–A003) — e-mail/senha; recuperar senha. **[S4–S5]**  
- [ ] **Perfil Interessado** (UI001–UI002) — hábitos, pets, horários, orçamento. **[S5–S6]**  
- [ ] **Anúncio do Ofertante** (UO001–UO003) — fotos, bairro/raio, aluguel + contas, regras; pausar/reativar. **[S6–S7]**  
- [ ] **Lista de Vagas + Filtros** (VIC001–VIC002) — localização, preço, nº moradores, pets etc. **[S8]**  
- [ ] **Interesse/Aceite + Detalhe da Vaga** (VIC003–VIC005). **[S9]**  
- [ ] **Match & Recomendações** (MC001–MC003) — índice de compatibilidade e ordenação. **[S10–S11]**  
- [ ] **Chat interno + Notificações** (CI001–CI003, N001–N003). **[S12–S13]**  
- [ ] **Segurança/Moderação** — bloquear/denunciar perfis. **[S12]**  
- [ ] **(Opcional) Gestão de Despesas** — registrar/dividir contas, lembretes. **[pós-MVP]**

**RNF alvo:** P95 feed ≤ 2,5s; disponibilidade ≥ 99,5%; LGPD (endereço **aproximado** antes do match); Acessibilidade AA. **[S14]**

---

## 🛠️ Tecnologias

- **Linguagem/Toolkit:** Kotlin, Jetpack Compose (Material 3, Navigation).  
- **Gerência de dependências UI:** **Compose BOM** (uma única versão controla todas as libs Compose).  
- **Build:** Gradle **Kotlin DSL** (`build.gradle.kts`) + **Version Catalog** (`gradle/libs.versions.toml`).  
- **Segredos:** **Secrets Gradle Plugin** com `secrets.properties` e `local.defaults.properties`.  
- **Notificações (planejado):** Firebase Cloud Messaging.  

---

## 📦 Estrutura (inicial)

```

app/
src/...

# futuras pastas:

# feature/match, feature/listings, feature/chat

# core/ui, core/data

gradle/libs.versions.toml

````

---

## 🧪 Como rodar (dev)

```bash
# 1) Clonar
git clone https://github.com/WendelRodriguesz/Roomie_Android
cd Roomie_Android

# 2) Abrir no Android Studio (Giraffe+), sincronizar Gradle

# 3) Segredos locais (opcional, p/ APIs):
#   app/local.defaults.properties  -> valores dummy (versionado)
#   app/secrets.properties         -> valores reais (NÃO versionar)
# Exemplo:
#   API_BASE_URL=https://dev.example.com
#   MAPS_API_KEY=REPLACE_ME

# 4) Build/Run
./gradlew assembleDebug
# ou "Run" no Android Studio (minSdk 24+)
````

> Dica: se usar **Infisical**, padronize segredos entre máquinas/CI:
> `infisical export --format=dotenv --output-file=./app/secrets.properties`

---

## 📐 Processos

### Padrão de commits

* **Conventional Commits** (ex.: `feat:`, `fix:`, `chore:`)
  Referências e guias úteis:

  * dev.to/renatoadorno/padroes-de-commits-commit-patterns-41co
  * github.com/iuricode/padroes-de-commits
  * gist.github.com/parmentf/359667bf23e08a1bd8241fbf47ecdef0

### Fluxo de PR

1. Abrir issue (ligada ao requisito/sprint).
2. Criar branch `feature/<id>-<curto>` ou `fix/<id>-<curto>`.
3. PR com checklist (build, testes, screenshots, links do Figma).
4. Code review + merge squash.

---

## 📎 Referências de projeto

* Compose BOM (gerir versões Compose com um único **BOM**).
* Especificação **Conventional Commits** (mensagens padronizadas).
