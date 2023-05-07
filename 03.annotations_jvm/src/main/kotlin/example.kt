import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject

interface Computer
interface Coffee
class Code
class CheekyChappuccino @Inject constructor() : Coffee
class AncienteFamilyPc @Inject constructor() : Computer

class Programmer @Inject constructor(val computer: Computer, val coffee: Coffee) {
    fun produce(): Code {
        //ancient caffeinated magic
        return Code()
    }
}

//all provides need to belong to a module
@Module
abstract class MyModule {
    @Binds
    abstract fun providesComputer(computer: AncienteFamilyPc): Computer

    @Binds
    abstract fun providesCoffee(coffee: CheekyChappuccino): Coffee

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
