import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{ActorMaterializer, Materializer}

object ReactiveExample extends App {

  implicit val system: ActorSystem = ActorSystem("ReactiveExample") // Crear un sistema de actores de Akka llamado "ReactiveExample"
  implicit val materializer: Materializer = ActorMaterializer() // Crear un materializador para gestionar la ejecución de flujos de Akka Streams

  val textField: String = "Este es un ejemplo de texto dinámico." // Definir una cadena de texto que representa el campo de entrada
  val characterCountDisplay: Sink[Int, _] = Sink.foreach(count => println(s"Total de caracteres: $count")) // Definir un destino que imprime en consola el total de caracteres

  val countCharacters: Flow[String, Int, _] = Flow[String].map(text => text.length) //Definir un flujo que transforma cada cadena de texto en su longitud

  val textSource: Source[String, _] = Source.single(textField) // Crear un origen (source) a partir del campo de texto

  val countingPipeline = textSource.via(countCharacters).to(characterCountDisplay) // Crear un pipeline conectando el origen, el flujo y el destino

  countingPipeline.run() // // Iniciar el procesamiento de datos

  // Esperar un tiempo para ver el resultado antes de apagar el sistema de actores
  Thread.sleep(2000) // Esperar 2000 milisegundos
  system.terminate() // Apagar el sistema de actores de Akka al final del programa
}
