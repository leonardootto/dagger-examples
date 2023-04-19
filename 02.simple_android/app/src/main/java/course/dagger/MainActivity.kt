package course.dagger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import course.dagger.ui.theme.SimpleDaggerTheme
import dagger.Provides
import javax.inject.Inject
import dagger.Module
import dagger.Component

interface Computer
interface Coffee
class Code

class Programmer @Inject constructor(val computer: Computer, val coffee: Coffee) {
    fun produce(): Code {
        //ancient caffeinated magic
        return Code()
    }
}

//all provides need to belong to a module
@Module
class MyModule {
    @Provides
    fun providesComputer(): Computer {
        class AncienteFamilyPc() : Computer
        return AncienteFamilyPc()
    }

    @Provides
    fun providesCoffee(): Coffee {
        class CheekyChappuccino() : Coffee
        return CheekyChappuccino()
    }
}

@Component(modules = [MyModule::class])
interface ProgrammerComponent {
    fun createProgrammer(): Programmer
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleDaggerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val programmerComponent = DaggerProgrammerComponent.builder()
                        .myModule(MyModule())
                        .build();

                    val programmer = programmerComponent.createProgrammer()
                    Greeting(programmer)
                }
            }
        }
    }
}

@Composable
fun Greeting(programmer: Programmer,modifier: Modifier = Modifier) {
    Text(
        text = "Hello $programmer!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleDaggerTheme {
        val programmerComponent = DaggerProgrammerComponent.builder()
            .myModule(MyModule())
            .build();

        val programmer = programmerComponent.createProgrammer()
        Greeting(programmer)
    }
}