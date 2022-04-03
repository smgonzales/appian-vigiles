package org.interview.appian.vigiles.impls

import org.interview.appian.vigiles.api.Building
import org.interview.appian.vigiles.api.City
import org.interview.appian.vigiles.api.CityNode
import org.interview.appian.vigiles.api.FireDispatch
import org.interview.appian.vigiles.api.exceptions.InvalidDimensionException
import org.interview.appian.vigiles.api.exceptions.OutOfCityBoundsException
import org.interview.appian.vigiles.firefighters.FireDispatchImpl

class CityImpl(xDimension: Int, yDimension: Int, fireStationLocation: CityNode) : City {

    private val fireStation: FireStation
    private val buildingGrid: Array<Array<Building>>
    private val fireDispatch: FireDispatch

    init {
        validateCityDimensions(xDimension, yDimension)
        fireStation = FireStation(fireStationLocation)
        buildingGrid = initBuildingGrid(xDimension, yDimension)
        fireDispatch = FireDispatchImpl(this)
    }

    override fun getFireStation(): Building {
        return fireStation
    }

    override fun getFireDispatch(): FireDispatch {
        return fireDispatch
    }

    override fun getXDimension(): Int {
        return buildingGrid.size
    }

    override fun getYDimension(): Int {
        return buildingGrid[0].size
    }

    @Throws(OutOfCityBoundsException::class)
    override fun getBuilding(xCoordinate: Int, yCoordinate: Int): Building {
        validateCoordinate(xCoordinate, yCoordinate)
        return buildingGrid[xCoordinate][yCoordinate]
    }

    @Throws(OutOfCityBoundsException::class)
    override fun getBuilding(cityNode: CityNode): Building {
        return getBuilding(cityNode.x, cityNode.y)
    }

    private fun initBuildingGrid(xDimension: Int, yDimension: Int): Array<Array<Building>> {
        return Array(xDimension) { x -> Array(yDimension) { y -> initBuilding(x, y) } }
    }

    private fun validateCityDimensions(xDimension: Int, yDimension: Int) {
        if (xDimension < 2) {
            throw InvalidDimensionException(xDimension)
        } else if (yDimension < 2) {
            throw InvalidDimensionException(yDimension)
        }
    }

    private fun initBuilding(x: Int, y: Int): Building {
        return if (x == fireStation.location.x && y == fireStation.location.y) {
            fireStation
        } else {
            BuildingImpl(CityNode(x, y))
        }
    }

    @Suppress("ComplexCondition")
    private fun validateCoordinate(xCoordinate: Int, yCoordinate: Int) {
        if (xCoordinate < 0 || yCoordinate < 0 || xCoordinate >= getXDimension() || yCoordinate >= getYDimension()) {
            throw OutOfCityBoundsException()
        }
    }
}
