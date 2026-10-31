# 📱 Guia Prático: Primeiro App Android com Kotlin (VS Code + Appetize.io)

Bem-vindo ao desenvolvimento Android moderno sem o peso do Android Studio! Neste tutorial, você aprenderá a criar, programar e compilar um app Android em **Kotlin** com **Jetpack Compose** usando apenas o **Visual Studio Code**, gerando o arquivo `.apk` e testando-o diretamente no navegador pelo **Appetize.io**.

---

## 🎯 O que vamos construir?

Um aplicativo interativo de boas-vindas:
1. O usuário digita o seu nome no campo de texto.
2. Clica no botão **"Dizer Olá"**.
3. O app exibe uma saudação dinâmica e personalizada na tela.

---

## 🛠️ Passo 1: Preparando o VS Code e Ferramentas

Para desenvolver com agilidade no **VS Code**, prepare as ferramentas essenciais:

### 1. Extensões Recomendadas no VS Code
Abra a aba de extensões (`Ctrl + Shift + X`) e instale:
- **Kotlin** (Mathias Frohlich) ou **Kotlin Language Support** (syntax highlight e autocomplete).
- **Extension Pack for Java** (Red Hat / Microsoft) — essencial para o suporte ao JDK e Gradle.
- **Gradle for Java** (facilita executar tarefas do Gradle diretamente pelo painel lateral).

### 2. Ferramentas no Sistema
- **Java JDK 17+**: Necessário para rodar o compilador Gradle.
- **Android SDK**: Necessário para compilar o APK (variáveis `ANDROID_HOME` apontando para o diretório do SDK).
- *(Dica de Ouro)*: O Google disponibiliza a ferramenta de terminal `android` (`curl -fsSL https://dl.google.com/android/cli/latest/linux_x86_64/install.sh | bash`), que permite criar projetos e gerenciar pacotes sem abrir nenhuma IDE pesada.

---

## 🚀 Passo 2: Criando a Estrutura do Projeto

No terminal integrado do VS Code (`Ctrl + ~`), execute o comando para gerar o projeto inicial:

```bash
android create empty-activity --name="OlaMundoKotlin" --namespace="com.exemplo.olamundo" --output=. --force
```

> **Se preferir criar manualmente**, a estrutura básica de um projeto Android com Gradle é:
> ```text
> ├── app/
> │   ├── build.gradle.kts
> │   └── src/
> │       └── main/
> │           ├── AndroidManifest.xml
> │           └── java/com/exemplo/olamundo/MainActivity.kt
> ├── build.gradle.kts
> ├── settings.gradle.kts
> └── gradlew (e gradlew.bat)
> ```

---

## 💻 Passo 3: O Código do Aplicativo (`MainActivity.kt`)

Abra o arquivo `app/src/main/kotlin/com/exemplo/olamundo/MainActivity.kt` (ou `.../java/...`) no VS Code e insira o código com suporte a saudação interativa, botão de limpar, contador de cliques e preview:

```kotlin
package com.exemplo.olamundo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GreetingScreen()
            }
        }
    }
}

@Composable
fun GreetingScreen() {
    // 1. Estados da tela: variáveis reativas
    var nameInput by remember { mutableStateOf("") }
    var displayedGreeting by remember { mutableStateOf("") }
    var clickCount by remember { mutableStateOf(0) }

    // 2. Coluna que alinha os componentes no centro da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título principal
        Text(
            text = "Meu Primeiro App",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de texto para digitar o nome
        OutlinedTextField(
            value = nameInput,
            onValueChange = { novoTexto -> nameInput = novoTexto },
            label = { Text("Qual é o seu nome?") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Linha com os botões de ação
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botão de interação principal
            Button(
                onClick = {
                    if (nameInput.isNotBlank()) {
                        clickCount++
                        displayedGreeting = "Olá, ${nameInput.trim()}! Seja bem-vindo ao Kotlin! 🚀"
                    } else {
                        displayedGreeting = "Por favor, digite seu nome acima! 😊"
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Dizer Olá", fontSize = 16.sp)
            }

            // Botão Limpar
            OutlinedButton(
                onClick = {
                    nameInput = ""
                    displayedGreeting = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Limpar", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Exibição da mensagem resultante
        if (displayedGreeting.isNotEmpty()) {
            Text(
                text = displayedGreeting,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // Exibição do contador de saudações
        if (clickCount > 0) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Saudações geradas: $clickCount",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

// 3. Pré-visualização para o editor
@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MaterialTheme {
        GreetingScreen()
    }
}
```

---

## 🧠 Passo 4: Entendendo a Lógica do Código

1. **`@Composable`**:
   Indica uma função que desenha a interface do usuário. No Compose, você não manipula botões por IDs em arquivos XML — você descreve diretamente em Kotlin como a tela deve se parecer.

2. **`remember { mutableStateOf(...) }`**:
   Armazena o estado dos componentes (`nameInput`, `displayedGreeting`, `clickCount`). Quando o valor de qualquer estado muda, o Compose re-executa dinamicamente apenas os componentes que dependem dele (*recomposição*).

3. **`Row` e `Column`**:
   A `Column` organiza os elementos verticalmente, e a `Row` posiciona os dois botões ("Dizer Olá" e "Limpar") lado a lado de forma responsiva (`Modifier.weight(1f)` e `spacedBy(12.dp)`).

4. **`OutlinedButton` vs `Button`**:
   O Jetpack Compose permite hierarquia visual: o `Button` preenchido serve como ação primária e o `OutlinedButton` serve como ação secundária para limpar a tela.

5. **`@Preview`**:
   Permite pré-visualizar a tela diretamente em ferramentas com suporte a Compose sem precisar compilar o APK completo para testar a interface.

---

## ⚙️ Passo 5: Gerando o APK no Terminal do VS Code

Antes de compilar, certifique-se de que o seu `app/build.gradle.kts` esteja configurado com uma versão estável e suportada pelo Android Gradle Plugin (como `compileSdk = 36` e `targetSdk = 36` para AGP 9.0.x):

```kotlin
android {
    compileSdk = 36
    defaultConfig {
        minSdk = 24
        targetSdk = 36
        ...
    }
}
```

Com o código pronto, vamos compilar o arquivo instalável `.apk`:

1. Abra o terminal integrado no VS Code (`Ctrl + ~` ou `Terminal > New Terminal`).
2. Execute a tarefa do Gradle para compilar a versão de depuração:
   ```bash
   ./gradlew assembleDebug
   ```
3. O Gradle compilará todo o código Kotlin e as bibliotecas do Jetpack Compose.
4. Quando a mensagem `BUILD SUCCESSFUL` aparecer, seu APK estará pronto em:
   ```text
   app/build/outputs/apk/debug/app-debug.apk
   ```

*(Dica: Se estiver no Linux/macOS e receber permissão negada, execute `chmod +x gradlew` antes).*

---

## 🌐 Passo 6: Emulando no Navegador com Appetize.io

O **Appetize.io** é um serviço em nuvem excelente porque executa o seu app em um emulador Android real dentro do navegador, sem consumir a memória RAM do seu computador.

### Passo a passo para testar:
1. Abra o navegador e acesse: [appetize.io/upload](https://appetize.io/upload).
2. Na caixa de upload, arraste ou selecione o arquivo gerado:
   `app/build/outputs/apk/debug/app-debug.apk`
3. Insira seu e-mail caso solicitado (o Appetize enviará o link do emulador) ou aguarde o redirecionamento imediato para a tela de visualização.
4. Clique em **"Tap to Play"** na tela do celular virtual.
5. O seu aplicativo será iniciado! Digite o nome no campo de texto, clique em **"Dizer Olá"**, teste o contador e use **"Limpar"**.

---

## 🎓 Desafios do Professor (Fixação)

Com os recursos de **Limpar** e **Contador de Cliques** já implementados, experimente os próximos desafios:

1. **Reset Total do Contador:** Modifique a ação do botão **"Limpar"** para também resetar o contador com `clickCount = 0`.
2. **Personalização Visual com Card:** Envolva o formulário dentro de um `Card` do Material 3 com bordas arredondadas e elevação.
3. **Validação de Caracteres:** Adicione uma verificação para exibir um aviso caso o nome digitado tenha menos de 3 letras.
