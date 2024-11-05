# Sprint 3 - Sistema de Gerenciamento de Produtos

## Equipe

- Nome do Grupo: **Datatech**
- Integrantes:
  - Ana Paula Nascimento Silva - RM552513
  - Calina Thalya Santana da Silva - RM552523
  - Geovana Ribeiro Domingos Silva - RM99646
  - Leonardo Camargo Lucena - RM552537
  - Nathan Nunes Calsonari RM552539

## Descrição do Projeto

Este projeto é um aplicativo Android para o gerenciamento de produtos que permite aos usuários cadastrar e listar produtos de maneira simples e eficiente. Além disso, o sistema também oferece funcionalidade de autenticação, onde os usuários podem se registrar e fazer login.

### Principais Funcionalidades:

- **Cadastro de Produto**: Permite que o usuário registre novos produtos fornecendo nome, descrição e preço.
- **Listagem de Produtos**: Exibe uma lista dos produtos cadastrados.
- **Autenticação**: O sistema conta com uma tela de login e registro de novos usuários.
- **API REST**: O aplicativo se conecta a uma API backend para gerenciar o cadastro e recuperação dos produtos, utilizando o Retrofit como cliente HTTP.

### Estrutura do Aplicativo

O aplicativo segue uma arquitetura organizada para facilitar a escalabilidade e manutenção, com separação de responsabilidades entre atividades, API, e modelos de dados. 

- **Retrofit**: Utilizado para chamadas à API (registro, login, listagem de produtos e adição de novos produtos).
- **ConstraintLayout e LinearLayout**: Usados para organizar os elementos visuais nas telas.

### Tecnologias Utilizadas:

- **Kotlin**: Linguagem de programação principal do aplicativo.
- **Retrofit**: Biblioteca para chamadas HTTP e integração com a API REST.
- **Gson**: Utilizado para conversão entre JSON e objetos Java/Kotlin.
- **Android SDK**: Plataforma para desenvolvimento de aplicativos móveis.

### Como Executar o Projeto

1. Clone o repositório:
   ```bash
   (https://github.com/GEOVANAAPROGRAMMER/ProductManager)

### Estrutura do Aplicativo

O aplicativo segue uma arquitetura organizada para facilitar a escalabilidade e manutenção, com separação de responsabilidades entre atividades, API, e modelos de dados. 

```plaintext
/sprint3-app/
│
├── /app/
│   ├── /src/
│   │   ├── /main/
│   │   │   ├── /java/com/example/sprint3/
│   │   │   │   ├── /api/               # Classes relacionadas à API (Retrofit, etc.)
│   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   ├── RetrofitClient.kt
│   │   │   │   ├── /models/             # Modelos de dados (LoginRequest, ProductRequest, etc.)
│   │   │   │   │   ├── Login.kt
│   │   │   │   │   ├── Product.kt
│   │   │   │   │   ├── Register.kt
│   │   │   │   ├── /activity/                 # Classes das atividades e telas (MainActivity, LoginActivity, etc.)
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── RegisterActivity.kt
│   │   │   │   │   ├── AddProductActivity.kt
│   │   │   │   │   ├── LoginActivity.kt
│   │   │   │   │   ├── ProductListActivity.kt
│   │   │   ├── /res/
│   │   │   │   ├── /layout/             # Arquivos de layout XML
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_register.xml
│   │   │   │   │   ├── activity_login.xml
│   │   │   │   │   ├── activity_add_product.xml
│   │   │   │   │   ├── activity_list_products.xml
│   │   │   │   ├── /drawable/           # Recursos gráficos, ícones
│   │   │   │   ├── /values/             # Strings, cores, dimensões
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── themes.xml
│   ├── /build/                          # Arquivos gerados na compilação
│   └── /libs/                           # Dependências externas (bibliotecas)
│
├── /gradle/                             # Arquivos Gradle e configurações
└── README.md   
