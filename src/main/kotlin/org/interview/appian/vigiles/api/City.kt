package org.interview.appian.vigiles.api

import org.interview.appian.vigiles.api.exceptions.OutOfCityBoundsException

interface City {

    /**
     * Get the city's FireStation. The FireStation is fireproof
     *
     * @return [Building] representing the FireStation
     */
    fun getFireStation(): Building

    /**
     * Get the city's FireDispatch.
     * @return the city's [FireDispatch]
     */
    fun getFireDispatch(): FireDispatch

    /**
     * Get the X dimension of the city
     *
     * @return the X dimension of the city
     */
    fun getXDimension(): Int

    /**
     * Get the Y dimension of the city
     *
     * @return the Y dimension of the city
     */
    fun getYDimension(): Int

    /**
     * Get the building at the given coordinates
     *
     * @param xCoordinate
     * @param yCoordinate
     * @return the [Building] at these coordinates
     * @throws OutOfCityBoundsException if the coordinates are out of bounds for this city
     */
    @Throws(OutOfCityBoundsException::class)
    fun getBuilding(xCoordinate: Int, yCoordinate: Int): Building

    /**
     * Get the building at the given location
     *
     * @param cityNode
     * @return the [Building] at this location
     * @throws OutOfCityBoundsException if the location is out of bounds for this city
     */
    @Throws(OutOfCityBoundsException::class)
    fun getBuilding(cityNode: CityNode): Building
}
