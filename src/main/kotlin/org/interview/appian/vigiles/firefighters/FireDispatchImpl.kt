package org.interview.appian.vigiles.firefighters

import org.interview.appian.vigiles.api.City
import org.interview.appian.vigiles.api.CityNode
import org.interview.appian.vigiles.api.FireDispatch
import org.interview.appian.vigiles.api.Firefighter

class FireDispatchImpl(private val city: City) : FireDispatch {

    private val firefighters = arrayListOf<Firefighter>()

    override fun setFirefighters(numFirefighters: Int) {
        firefighters.clear()

        repeat(numFirefighters) {
            firefighters.add(FirefighterImpl(city))
        }
    }

    override fun getFirefighters(): List<Firefighter> {
        return firefighters
    }

    override fun dispatchFirefighters(vararg burningBuildings: CityNode) {
        val buildingsInFlames = ArrayDeque(burningBuildings.toList())
        val firefighterQueue = ArrayDeque(firefighters)

        while (buildingsInFlames.isNotEmpty()) {
            // Dequeue each burning building and retrieve our next fire fighter.
            val burningLocation = buildingsInFlames.removeFirst()
            val firefighter = firefighterQueue.removeFirst()

            // Fight the fire and add the fire fighter back to the end of the queue to fight another fire.
            firefighter.fightFire(burningLocation)
            firefighterQueue.addLast(firefighter)
        }
    }
}
