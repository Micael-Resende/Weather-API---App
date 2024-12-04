# Weather API App

## Descrição
Este é um aplicativo Android que consome dados de uma API de clima para exibir informações meteorológicas de uma cidade especificada pelo usuário. O aplicativo também utiliza uma API de imagens para definir um fundo dinâmico baseado no nome da cidade.

## Funcionalidades
- Busca de clima por nome da cidade.
- Exibição de:
  - Nome da cidade com ícones representativos (mapa e bandeira do país).
  - Temperatura atual, máxima e mínima.
  - Umidade e velocidade do vento.
  - Descrição do clima (exemplo: "Céu limpo").
- Fundo dinâmico baseado na cidade consultada.
- Indicador de carregamento enquanto busca as informações.
- Tratamento de erros para entradas inválidas ou problemas na conexão com as APIs.

## Tecnologias Utilizadas
- **Linguagem:** Kotlin
- **IDE:** Android Studio
- **APIs Externas:**
  - [OpenWeatherMap API](https://openweathermap.org/api) para dados meteorológicos.
  - [Unsplash API](https://unsplash.com/developers) para imagens dinâmicas.
  - [FlagsAPI](https://flagsapi.com/) para bandeiras dos países.
- **Bibliotecas:**
  - Glide para carregamento de imagens.
  - ConstraintLayout para layout responsivo.

## Requisitos
- **SDK Mínimo:** 24
- **SDK Alvo:** 35
- **Versão do Glide:** 4.15.1

## Configuração e Execução

1. **Clonar o repositório:**
   ```bash
   git clone <url-do-repositorio>
   ```

2. **Abrir no Android Studio:**
   - Navegue até o diretório do projeto e abra o arquivo `build.gradle` na IDE.

3. **Adicione suas chaves das APIs:**
   - Substitua as variáveis `apiKeyWeather`, `apiKeyUnsplash` e `apiCountryURL` com suas chaves e URLs no arquivo `MainActivity.kt`.

4. **Executar o projeto:**
   - Conecte um dispositivo ou utilize um emulador.
   - Clique em "Run" para compilar e executar o aplicativo.

## Exemplo do Projeto
<img src="https://github.com/Micael-Resende/Weather-API---App/blob/master/images/Image-app.jpg" alt="Interface do App" width="300">

## Estrutura do Projeto

- `MainActivity.kt`: Lógica principal do aplicativo, incluindo chamadas às APIs e atualizações na interface.
- `res/`: Recursos do aplicativo, incluindo layouts, imagens e strings.
  - `drawable/`: Contém ícones e imagens.
  - `layout/activity_main.xml`: Layout principal do aplicativo.
  - `values/styles.xml`: Definição de estilos personalizados.
- `AndroidManifest.xml`: Declarações de permissões e configurações do aplicativo.

## Permissões Necessárias
- Acesso à Internet:
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  ```

---
Feito com ❤ por **Micael Resende**.
