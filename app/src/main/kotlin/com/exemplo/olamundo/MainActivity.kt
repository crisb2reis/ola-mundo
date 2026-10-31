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
    // Ponto de entrada do aplicativo Android:
    // O método onCreate é chamado pelo sistema operacional assim que a tela nasce.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent é a ponte entre a Activity clássica e o Jetpack Compose.
        // Aqui definimos que a interface será 100% desenhada com código Kotlin declarativo.
        setContent {
            // Surface é o "papel de fundo" da nossa aplicação, aplicando a cor de fundo padrão do Material 3.
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                // Chamamos a nossa função Composable principal da tela
                GreetingScreen()
            }
        }
    }
}

/**
 * 🎓 AULA SOBRE JETPACK COMPOSE:
 * A anotação @Composable diz ao compilador que esta função transforma dados em interface visual.
 * Em vez de criar arquivos XML e buscar elementos por ID (findViewById), aqui descrevemos
 * a tela como ela deve ser em função do estado atual.
 */
@Composable
fun GreetingScreen() {
    // -----------------------------------------------------------------------------------------
    // 1. ESTADO REATIVO (A "memória" da nossa tela):
    // 'remember' faz com que a variável sobreviva às recomposições (quando a tela se redesenha).
    // 'mutableStateOf' cria um estado observável: sempre que o seu valor muda, o Compose
    // redesenha automaticamente apenas os componentes que leem essa variável.
    // -----------------------------------------------------------------------------------------
    
    // Armazena o texto digitado pelo usuário no campo de entrada
    var nameInput by remember { mutableStateOf("") }
    
    // Armazena a mensagem de saudação que será exibida ao clicar no botão
    var displayedGreeting by remember { mutableStateOf("") }
    
    // Contador: armazena quantas saudações válidas foram geradas até agora
    var clickCount by remember { mutableStateOf(0) }

    // -----------------------------------------------------------------------------------------
    // 2. ESTRUTURA VISUAL (Layout vertical):
    // A 'Column' posiciona os elementos um embaixo do outro (de cima para baixo).
    // - fillMaxSize: ocupa toda a largura e altura da tela do dispositivo.
    // - padding(24.dp): deixa uma margem de segurança nas bordas para não colar na borda física.
    // - CenterHorizontally e Center: garante que todo o conteúdo fique perfeitamente centralizado.
    // -----------------------------------------------------------------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título principal do aplicativo
        Text(
            text = "Meu Primeiro App",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Spacer cria um espaço vazio (respiro visual) de 24dp entre o título e o campo
        Spacer(modifier = Modifier.height(24.dp))

        // Campo de entrada de texto moderno (com borda e rótulo flutuante)
        OutlinedTextField(
            value = nameInput, // O campo reflete o valor atual da nossa variável de estado
            onValueChange = { novoTexto -> nameInput = novoTexto }, // Sempre que o usuário digita uma letra, atualizamos o estado
            label = { Text("Qual é o seu nome?") },
            singleLine = true, // Impede que o usuário quebre linha com 'Enter'
            modifier = Modifier.fillMaxWidth() // O campo se estica na largura total disponível
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------------------------------------------------------------
        // 3. LINHA DE AÇÕES (Layout horizontal):
        // A 'Row' posiciona os botões lado a lado.
        // - spacedBy(12.dp): cria um espaçamento uniforme de 12dp entre os botões.
        // -------------------------------------------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // BOTÃO PRINCIPAL (Preenchido):
            // weight(1f) divide o espaço da linha igualmente com o outro botão (50% para cada)
            Button(
                onClick = {
                    // Lógica de negócio: valida se o usuário digitou algo antes de cumprimentar
                    if (nameInput.isNotBlank()) {
                        clickCount++ // Incrementa nosso contador de saudações
                        displayedGreeting = "Olá, ${nameInput.trim()}! Seja bem-vindo ao Kotlin! 🚀"
                    } else {
                        displayedGreeting = "Por favor, digite seu nome acima! 😊"
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Dizer Olá", fontSize = 16.sp)
            }

            // BOTÃO SECUNDÁRIO (Contornado / Outlined):
            // Usado para ações secundárias ou de reset, mantendo uma boa hierarquia visual
            OutlinedButton(
                onClick = {
                    // Limpa o campo de texto e remove a mensagem exibida na tela
                    nameInput = ""
                    displayedGreeting = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Limpar", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // -------------------------------------------------------------------------------------
        // 4. RENDERIZAÇÃO CONDICIONAL:
        // No Compose, elementos condicionais só são desenhados na tela se a condição for verdadeira.
        // -------------------------------------------------------------------------------------

        // Só desenha o texto da saudação se ela não estiver vazia
        if (displayedGreeting.isNotEmpty()) {
            Text(
                text = displayedGreeting,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // Só desenha o contador se o usuário já tiver gerado pelo menos 1 saudação
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

/**
 * 🛠️ FERRAMENTA DO DESENVOLVEDOR:
 * O @Preview permite ao desenvolvedor ver a interface desenhada no próprio editor
 * sem a necessidade de gerar um APK ou rodar um emulador/celular.
 */
@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MaterialTheme {
        GreetingScreen()
    }
}