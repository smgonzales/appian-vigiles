package org.interview.appian.vigiles.firefighters

import org.interview.appian.vigiles.api.City
import org.interview.appian.vigiles.api.CityNode
import org.interview.appian.vigiles.api.FireDispatch
import org.interview.appian.vigiles.api.Firefighter
import org.interview.appian.vigiles.api.RouteMap

class FireDispatchImpl(private val city: City) : FireDispatch {

    private val firefighters = arrayListOf<Firefighter>()
    private val routeMap: RouteMap = mutableMapOf()

    init {
        createRouteMap()
    }

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
            firefighter.fightFire(burningLocation, routeMap)
            firefighterQueue.addLast(firefighter)
        }
    }

    /**
     * Build our adjacent list for our DFS algorithm.  We'll reuse the list for each firefighter instead
     * of having to build the list individually for each firefighter and recompute neighbor boundaries.
     */
    private fun createRouteMap() {
        val xDimension = city.getXDimension()
        val yDimension = city.getYDimension()

        for (x in 0 until xDimension) {
            for (y in 0 until yDimension) {
                val building = city.getBuilding(x, y)
                val neighbors = routeMap.computeIfAbsent(building.location) { mutableListOf() }

                if ((x - 1) >= 0) {
                    neighbors.add(city.getBuilding((x - 1), y))
                }

                if ((x + 1) < xDimension) {
                    neighbors.add(city.getBuilding((x + 1), y))
                }

                if ((y - 1) >= 0) {
                    neighbors.add(city.getBuilding(x, (y - 1)))
                }

                if ((y + 1) < yDimension) {
                    neighbors.add(city.getBuilding(x, (y + 1)))
                }
            }
        }
    }
}
