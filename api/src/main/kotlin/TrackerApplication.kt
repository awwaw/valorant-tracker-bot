import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.example.api"])
class TrackerApplication

fun main(args: Array<String>) {
    runApplication<TrackerApplication>(*args)
}
