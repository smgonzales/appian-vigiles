package org.interview.appian.vigiles.api

interface FireDispatch {

    /**
     * Hires a number of firefighters
     * @param numFirefighters
     */
    fun setFirefighters(numFirefighters: Int)

    /**
     * Get the list of firefighters
     * @return
     */
    fun getFirefighters(): List<Firefighter>

    /**
     * The FireDispatch will be notified of burning buildings via this method. It will then dispatch the
     * firefighters and extinguish the fires. We want to optimize for total distance traveled by all firefighters
     * @param burningBuildings list of locations with burning buildings
     */
    fun dispatchFirefighters(vararg burningBuildings: CityNode)
}
