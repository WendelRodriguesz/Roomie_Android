Padrão por feature (pasta por tela/fluxo):
feature/<nome>/
  - presentation/: ViewModel, State, Event (UDF/MVVM).
  - ui/: Composables stateless (Screen/Route).
  - navigation/: entradas no NavGraph da feature.
  - data/: repositórios/fonte de dados (se existir na feature).
  - domain/: use cases (opcional, quando a regra complica).
Evitar dependências cruzadas entre features.
