package com.example.activity4

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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.activity4.Data.DataForm
import com.example.activity4.Data.DataSource.jenis
import com.example.activity4.Data.DataSource.status
import com.example.activity4.ui.theme.Activity4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Activity4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier){
    Text(
        text = "Hello $name",
        modifier = modifier
        )
}
@Preview(showBackground = true)

@Composable
fun GreetingPreview() {
    Activity4Theme {
        TampilLayout()
    }
}
@Composable
fun TampilLayout(
    modifier: Modifier = Modifier
){
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Column (verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ){
            TampilForm()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()){
    var textNama by remember {
        mutableStateOf("")
    }
    var textTlp by remember {
        mutableStateOf("")
    }
    var textalamat by remember {
        mutableStateOf("")
    }
    var textemail by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState

    Text(text = "Register", fontSize = 15.sp, fontWeight = FontWeight.Bold)
    Text(text = "Create Your Account", fontSize = 25.sp, fontWeight = FontWeight.Bold)

    OutlinedTextField(
        value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Username")},
        onValueChange = {
            textNama = it
        }
    )
    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telepon")},
        onValueChange = {
            textTlp = it
        }
    )
    OutlinedTextField(
        value = textemail,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email")},
        onValueChange = {
            textemail = it
        } )

    SelectJK(options = jenis.map{ id -> context.resources.getString(id)},
        onSelectionChanged = { cobaViewModel.setJenisK(it)})

    SelectSTT(options = status.map{ id -> context.resources.getString(id)},
        onSelectionChanged = { cobaViewModel.setJenisK(it)})

    OutlinedTextField(
        value = textalamat,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Alamat")},
        onValueChange = {
            textalamat = it
        } )
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {cobaViewModel.insertData(textNama, textTlp, textalamat, textemail, dataForm.sex, dataForm.sts)
        }
    )
    {
        Text(
            text = stringResource(R.string.submit),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(70.dp))
    TextHasil(
        alamatnya = cobaViewModel.alamat,
        jenisnya = cobaViewModel.jenisKl,
        emailnya = cobaViewModel.email,
        statusnya = cobaViewModel.status,

    )
}
@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }
    Row(modifier = Modifier.padding(10.dp)) {
        Text(text = "Jenis Kelamin")
        options.forEach { item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    })
                Text(item)
            }
        }
    }
}
@Composable
fun SelectSTT(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }
    Row(modifier = Modifier.padding(10.dp)) {
        Text(text = "Status")
        options.forEach { item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    })
                Text(item)
            }
        }
    }
}

@Composable
fun TextHasil(jenisnya: String, statusnya: String, alamatnya: String, emailnya: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Status : " + statusnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Alamat : " + alamatnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Email : " + emailnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
    }
}
