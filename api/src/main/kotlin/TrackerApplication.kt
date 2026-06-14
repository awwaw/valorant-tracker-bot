import org.example.config.HenrikApiProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackages = ["org.example"])
@EnableConfigurationProperties(HenrikApiProperties::class)
@EnableFeignClients(basePackages = ["org.example.client"])
@EnableScheduling
class TrackerApplication

fun main(args: Array<String>) {
    runApplication<TrackerApplication>(*args)
}
