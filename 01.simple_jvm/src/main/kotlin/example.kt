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

fun main(){
    val programmerComponent = DaggerProgrammerComponent.builder()
        .build();

    val programmer = programmerComponent.createProgrammer()
    print(programmer)
}
