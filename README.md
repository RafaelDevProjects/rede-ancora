# 📱 Aplicativo Rede Âncora + API Spring Boot

Este projeto consiste em um **sistema composto por dois módulos integrados**:

1. **Backend Spring Boot**: Serve como intermediário entre o Firebase e o aplicativo.
2. **Aplicativo Android**: Consome a API REST para exibir e interagir com os dados de peças automotivas.
---

## 📌 Objetivo do Projeto

O projeto nasceu a partir da necessidade de **facilitar o relacionamento entre mecânicos e as lojas da Rede Âncora**, além de **otimizar o processo de cotação e compra de peças automotivas**. Através de uma interface prática e moderna, o aplicativo torna possível:

- Consultar peças por categoria
- Visualizar detalhes e imagens
- Buscar peças por nome
- Marcar peças como favoritas
- Explorar categorias com imagens ilustrativas
- Acessar rapidamente o perfil e favoritos

---

## 🚀 Funcionalidades

| Função                                           | Descrição                                                                           |
|--------------------------------------------------|-------------------------------------------------------------------------------------|
| 🏁 Tela de Introdução                            | Interface inicial com botão de entrada para o app                                  |
| 🏠 Tela Principal (`MainActivity`)               | Exibe peças populares, categorias e barra de busca                                 |
| 🛠️ Filtro por Categoria                          | Permite visualizar peças apenas da categoria selecionada                           |
| 🔍 Busca Inteligente                             | Filtro dinâmico por nome das peças digitado pelo usuário                           |
| ⭐ Favoritos (`ActivityFavorite`)                 | Lista todas as peças marcadas como favoritas pelo usuário                          |
| 🔎 Detalhes da Peça (`DetailActivity`)           | Mostra todas as informações da peça (imagem, marca, avaliação, etc.)               |
| 👤 Perfil (`ProfileActivity`)                    | Tela com opção de voltar e informações do app (possibilidade de expansão)          |

---

### ✅ Backend Spring Boot

| Metodo HTTP    | Função                        | Descrição                                                                 |
|----------------|-------------------------------|---------------------------------------------------------------------------|
| GET            | /api/pecas                    | Lista todas as peças                                                      |
| GET            | /api/pecas/favoritas          | Retorna todas as peças marcadas como favoritas                            |
| GET            | /api/categorias               | Retorna todas as categorias de peças                                      |
| GET            | /api/pecas/{id}               | Retorna uma peça específica pelo id                                       |
| PUT            | /api/pecas/{id}/favorito      | Atualiza o status de favorito de uma peça (requer apiKey)                 |

---

## 🧱 Estrutura de Código

```
📁 aplicativo-rede-ancora/
├── 📁 app/                              # Aplicativo Android
│   ├── 📁 br.com.redeAncora.app/
│   │   ├── 📁 Activity/
│   │   │   ├── IntroActivity.java
│   │   │   ├── MainActivity.java
│   │   │   ├── DetailActivity.java
│   │   │   ├── ActivityFavorite.java
│   │   │   ├── ProfileActivity.java
│   │   │   └── BaseActivity.java
│   │   ├── 📁 Adapter/
│   │   │   ├── PecasAdapter.java
│   │   │   └── CategoryAdapter.java
│   │   ├── 📁 Domain/
│   │   │   ├── PecasDomain.java
│   │   │   └── CategoryDomain.java
│   │   ├── 📁 Services/
│   │   │   ├── ApiService.java
│   │   │   └── RetrofitClient.java
│   │   ├── 📁 dto/
│   │   │   ├── PecaDTO.java
│   │   │   └── CategoriaDTO.java
│   │   ├── 📁 res/
│   │   │   └── layout/                # Layouts XML utilizados com ViewBinding
│   └── └── Firebase/                 # Configuração do Firebase no app
│
├── 📁 api/                              # API Spring Boot
│   ├── 📁 br.com.redeancora.api/
│   │   ├── ApiApplication.java        # Classe principal da aplicação Spring
│   │   ├── 📁 config/
│   │   │   └── FirebaseConfig.java    # Inicialização do Firebase SDK
│   │   ├── 📁 controller/
│   │   │   └── PecaController.java    # Endpoints REST da API (COLOCAR SECRET_KEY aqui)
│   │   ├── 📁 dto/
│   │   │   ├── PecaDTO.java
│   │   │   └── CategoriaDTO.java
│   │   └── 📁 service/
│   │       └── FirebaseService.java   # Integração com Realtime Database do Firebase
│   └── 📁 resources/
│       ├── application.properties     # Configurações Spring
│       └── firebase-service-account.json (NÃO COMITAR!) 🔒
│
├── .gitignore
├── README.md
├── build.gradle / build.gradle.kts
└── settings.gradle / settings.gradle.kts
```

---

## 🗃️ Banco de Dados

- **Firebase Realtime Database**
- **Estrutura:**
  - **Category:** Armazena as categorias de peças.
    - **Campos:** `id` (int), `title` (String), `picUrl` (String)
  - **Pecas:** Armazena os detalhes das peças automotivas.
    - **Campos:** `title` (String), `description` (String), `detalhes` (String), `category` (String), `marca` (String), `price` (double), `rating` (double), `isFavorito` (boolean), `picUrl` (String)
- **Relacionamento Lógico:**  
  O campo `category` em **Pecas** faz referência ao `title` da **Category**, indicando a qual categoria a peça pertence.

---

## 🧰 Tecnologias Utilizadas

- **Android Studio + Java**
- **Firebase Realtime Database & Storage**
- **ViewBinding** para gerenciamento seguro dos layouts
- **Glide** para carregamento e exibição de imagens
- **RecyclerView** e **Adapters customizados** para listagem dos itens

---



## 🧪 Como Rodar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/RafaelDevProjects/appMobileRedeAncora.git
   ```
2. **Abra o projeto no Android Studio:**
   - Certifique-se de ter o Android Studio instalado.
3. **Execute o aplicativo:**
   - Utilize um dispositivo físico ou emulador Android para rodar o app.
4. **Verifique as dependências:**
   - Certifique-se de que as dependências do Firebase, Glide e ViewBinding estão configuradas no `build.gradle`.

---

## 🔐 Configuração da SECRET_KEY (Chave de Segurança)

Para proteger a escrita no Firebase Realtime Database, o aplicativo utiliza uma **chave secreta** (`SECRET_KEY`) que deve ser enviada junto com os dados ao marcar uma peça como favorita. Essa chave é exigida pelas regras de segurança do Firebase para validar a permissão de escrita.

### 📍 Onde configurar

A constante `SECRET_KEY` está localizada no arquivo `DetailActivity.java`, na linha:

```java
final String SECRET_KEY = "ADICIONAR_SECRET_KEY"; //ADICIONAR A SECRET KEY AQUI
```

> 🔐 Substitua `"ADICIONAR_SECRET_KEY"` pela mesma chave usada nas regras do Firebase.

---

### ✅ Recomendação de segurança

- Mantenha a `SECRET_KEY` fora de repositórios públicos.
- Se possível, extraia essa chave para um arquivo `.env`, uma `BuildConfig`, ou use Firebase Remote Config para carregar dinamicamente.

## 📸 Prints & Demonstrações

> **Dica:** Insira aqui os prints das telas (Intro, Main, Detail, Favorites, Perfil) para ilustrar a interface e navegação do app.

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e de prototipagem. Pode ser adaptado ou expandido para uso real com ajustes adicionais no backend, autenticação e segurança.

---

## 👥 Contribuição

Se você deseja contribuir para este projeto, sinta-se à vontade para abrir issues ou enviar pull requests.

---

## 💬 Contato

Para dúvidas ou sugestões, entre em contato através de [seu email] ou [seu perfil no GitHub].
