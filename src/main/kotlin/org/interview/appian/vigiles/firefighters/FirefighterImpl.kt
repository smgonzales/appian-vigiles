package org.interview.appian.vigiles.firefighters

import org.interview.appian.vigiles.api.City
import org.interview.appian.vigiles.api.CityNode
import org.interview.appian.vigiles.api.Firefighter
import org.interview.appian.vigiles.api.RouteMap

class FirefighterImpl(private val city: City) : Firefighter {

    private var location = city.getFireStation().location
    private var distance = 0

    override fun getLocation(): CityNode {
        return location
    }

    override fun distanceTraveled(): Int {
        return distance
    }

    override fun fightFire(burningLocation: CityNode, routeMap: RouteMap) {
        // Queue initialized with current location of firefighter.
        val queue = ArrayDeque<CityNode>()
        queue.add(getLocation())

        // Keep track of where we've been.
        val visited = mutableMapOf<CityNode, Boolean>()
        visited[getLocation()] = true

        // Used to keep track of the prior travel location from each city node as the firefighter travels.
        val priorLocations = mutableMapOf<CityNode, CityNode>()

        travelToBuilding(burningLocation, queue, visited, priorLocations, routeMap)
        val path = resolvePath(burningLocation, priorLocations)

        location = path.last()
        distance += (path.size - 1)

        city.getBuilding(location).extinguishFire()
    }

    /**
     * Alternative recursive DFS using adjacent list (RouteMap) and queue approach to find
     * shortest path to burning location.
     */
    private fun travelToBuilding(
        burningLocation: CityNode,
        queue: ArrayDeque<CityNode>,
        visited: MutableMap<CityNode, Boolean>,
        priorLocations: MutableMap<CityNode, CityNode>,
        routeMap: RouteMap
    ) {
        if (queue.isNotEmpty()) {
            val location = queue.removeFirst()
            val neighbors = routeMap[location]

            if (neighbors != null) {
                for (neighbor in neighbors) {
                    if (visited[neighbor.location] != true) {
                        visited[neighbor.location] = true
                        priorLocations[neighbor.location] = location

                        if (neighbor.location == burningLocation) {
                            queue.clear()
                            break
                        } else {
                            queue.add(neighbor.location)
                        }
                    }
                }
            }

            travelToBuilding(burningLocation, queue, visited, priorLocations, routeMap)
        }
    }

    /**
     * Resolve the DFS path by examining the prior traveled locations.
     *
     * @return [List<CityNode>] city paths to from a starting city node to the burning building.
     */
    private fun resolvePath(
        burningLocation: CityNode,
        priorLocation: MutableMap<CityNode, CityNode>
    ): List<CityNode> {
        val path = mutableListOf<CityNode>()
        var nextLocation: CityNode? = burningLocation

        // Work backwards from burning location to start location by examining map to determine path list.
        while (nextLocation != null) {
            path.add(nextLocation)
            nextLocation = priorLocation[nextLocation]
        }

        path.reverse()
        return path
    }
}
