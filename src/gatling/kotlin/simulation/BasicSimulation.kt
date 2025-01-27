package simulation

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import java.time.Duration

class BasicSimulation: Simulation() {
    private val protocol = http
        .baseUrl("http://localhost:8080")
        .contentTypeHeader("application/json")

    private val scn = scenario("Call to Simulation API")
        .exec(
            http("Run loan simulation")
                .post("/simulation")
                .body(ElFileBody("json/request.json"))
                .check(status().`is`(200))
        )

    init {
        setUp(scn.injectOpen(
            nothingFor(Duration.ofSeconds(5)),
            rampUsers(10000).during(Duration.ofSeconds(1))
        )).protocols(protocol)
    }
}