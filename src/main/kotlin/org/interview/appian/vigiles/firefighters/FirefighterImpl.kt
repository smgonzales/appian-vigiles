package org.interview.appian.vigiles.firefighters

import org.interview.appian.vigiles.api.Building
import org.interview.appian.vigiles.api.City
import org.interview.appian.vigiles.api.CityNode
import org.interview.appian.vigiles.api.Firefighter

class FirefighterImpl(private val city: City) : Firefighter {

    private var location = city.getFireStation().location
    private var distance = 0

    override fun getLocation(): CityNode {
        return location
    }

    override fun distanceTraveled(): Int {
        return distance
    }

    override fun fightFire(burningLocation: CityNode) {
        // Used to keep track of the prior travel location from each city node as the firefighter travels.
        val priorLocations = mutableMapOf<CityNode, CityNode>()

        travelToBuilding(burningLocation, priorLocations)
        val path = resolvePath(burningLocation, priorLocations)

        location = path.last()
        distance += (path.size - 1)

        city.getBuilding(location).extinguishFire()
    }

    /**
     * DFS using adjacent list (RouteMap) and iterative queue approach to find shortest path to burning location.
     * Just a slight variation with using a prebuilt neighbor map/list.
     */
    private fun travelToBuilding(
        burningLocation: CityNode,
        priorLocations: MutableMap<CityNode, CityNode>
    ) {
        // Queue initialized with current location of firefighter.
        val queue = ArrayDeque<CityNode>()
        queue.add(getLocation())

        // Keep track of where we've been.
        val visited = Array(city.getXDimension()) { Array(city.getYDimension()) { false } }
        visited[location.x][location.y] = true

        // DFS algorithm.  We'll stop when we find our burning building.
        while (queue.isNotEmpty()) {
            val location = queue.removeFirst()
            val neighbors = getNeighbors(location)

            neighbors.forEach { neighbor ->
                if (!visited[neighbor.location.x][neighbor.location.y]) {
                    visited[neighbor.location.x][neighbor.location.y] = true
                    priorLocations[neighbor.location] = location

                    if (neighbor.location == burningLocation) {
                        queue.clear()
                        return
                    } else {
                        queue.add(neighbor.location)
                    }
                }
            }
        }
    }

    /**
     * Get neighboring buildings to the specified location.
     */
    private fun getNeighbors(location: CityNode): List<Building> {
        val neighbors = mutableListOf<Building>()

        val xDimension = city.getXDimension()
        val yDimension = city.getYDimension()

        if ((location.x - 1) >= 0) {
            neighbors.add(city.getBuilding((location.x - 1), location.y))
        }

        if ((location.x + 1) < xDimension) {
            neighbors.add(city.getBuilding((location.x + 1), location.y))
        }

        if ((location.y - 1) >= 0) {
            neighbors.add(city.getBuilding(location.x, (location.y - 1)))
        }

        if ((location.y + 1) < yDimension) {
            neighbors.add(city.getBuilding(location.x, (location.y + 1)))
        }

        return neighbors
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
