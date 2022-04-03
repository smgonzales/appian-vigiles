package org.interview.appian.vigiles.api

typealias RouteMap = MutableMap<CityNode, MutableList<Building>>

interface Firefighter {

    /**
     * Get the firefighter's current location. Initially, the firefighter should be at the FireStation
     *
     * @return [CityNode] representing the firefighter's current location
     */
    fun getLocation(): CityNode

    /**
     * Get the total distance traveled by this firefighter. Distances should be represented using TaxiCab
     * Geometry: https://en.wikipedia.org/wiki/Taxicab_geometry
     *
     * @return the total distance traveled by this firefighter
     */
    fun distanceTraveled(): Int

    /**
     * Sends a firefighter to fight a fire at the specified city location with the given route map.
     */
    fun fightFire(burningLocation: CityNode, routeMap: RouteMap)
}
