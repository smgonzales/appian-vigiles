package org.interview.appian.vigiles.api

import org.interview.appian.vigiles.api.exceptions.FireproofBuildingException
import org.interview.appian.vigiles.impls.CityImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasicScenarios {

    @Test
    @Throws(FireproofBuildingException::class)
    fun singleFire() {
        val basicCity: City = CityImpl(5, 5, CityNode(0, 0))
        val fireDispatch = basicCity.getFireDispatch()

        val fireNode = CityNode(0, 1)
        Pyromaniac.setFire(basicCity, fireNode)

        fireDispatch.setFirefighters(1)
        fireDispatch.dispatchFirefighters(fireNode)

        assertFalse(basicCity.getBuilding(fireNode).isBurning)
    }

    @Test
    @Throws(FireproofBuildingException::class)
    fun singleFireDistanceTraveledDiagonal() {
        val basicCity: City = CityImpl(2, 2, CityNode(0, 0))
        val fireDispatch = basicCity.getFireDispatch()

        // Set fire on opposite corner from Fire Station
        val fireNode = CityNode(1, 1)
        Pyromaniac.setFire(basicCity, fireNode)

        fireDispatch.setFirefighters(1)
        fireDispatch.dispatchFirefighters(fireNode)
        val firefighter = fireDispatch.getFirefighters()[0]

        assertEquals(2, firefighter.distanceTraveled().toLong())
        assertEquals(fireNode, firefighter.getLocation())
    }

    @Test
    @Throws(FireproofBuildingException::class)
    fun singleFireDistanceTraveledAdjacent() {
        val basicCity: City = CityImpl(2, 2, CityNode(0, 0))
        val fireDispatch = basicCity.getFireDispatch()

        // Set fire on adjacent X position from Fire Station
        val fireNode = CityNode(1, 0)
        Pyromaniac.setFire(basicCity, fireNode)

        fireDispatch.setFirefighters(1)
        fireDispatch.dispatchFirefighters(fireNode)
        val firefighter = fireDispatch.getFirefighters()[0]

        assertEquals(1, firefighter.distanceTraveled().toLong())
        assertEquals(fireNode, firefighter.getLocation())
    }

    @Test
    @Throws(FireproofBuildingException::class)
    fun simpleDoubleFire() {
        val basicCity: City = CityImpl(2, 2, CityNode(0, 0))
        val fireDispatch = basicCity.getFireDispatch()

        val fireNodes = arrayOf(
            CityNode(0, 1),
            CityNode(1, 1)
        )

        Pyromaniac.setFires(basicCity, fireNodes)

        fireDispatch.setFirefighters(1)
        fireDispatch.dispatchFirefighters(*fireNodes)
        val firefighter = fireDispatch.getFirefighters()[0]

        assertEquals(2, firefighter.distanceTraveled().toLong())
        assertEquals(fireNodes[1], firefighter.getLocation())

        assertFalse(basicCity.getBuilding(fireNodes[0]).isBurning)
        assertFalse(basicCity.getBuilding(fireNodes[1]).isBurning)
    }

    @Test
    @Throws(FireproofBuildingException::class)
    fun doubleFirefighterDoubleFire() {
        val basicCity: City = CityImpl(2, 2, CityNode(0, 0))
        val fireDispatch = basicCity.getFireDispatch()

        val fireNodes = arrayOf(
            CityNode(0, 1),
            CityNode(1, 0)
        )

        Pyromaniac.setFires(basicCity, fireNodes)

        fireDispatch.setFirefighters(2)
        fireDispatch.dispatchFirefighters(*fireNodes)
        val firefighters = fireDispatch.getFirefighters()

        var totalDistanceTraveled = 0
        var firefighterPresentAtFireOne = false
        var firefighterPresentAtFireTwo = false

        for (firefighter in firefighters) {
            totalDistanceTraveled += firefighter.distanceTraveled()

            if (firefighter.getLocation() == fireNodes[0]) {
                firefighterPresentAtFireOne = true
            }
            if (firefighter.getLocation() == fireNodes[1]) {
                firefighterPresentAtFireTwo = true
            }
        }

        assertEquals(2, totalDistanceTraveled.toLong())

        assertTrue(firefighterPresentAtFireOne)
        assertTrue(firefighterPresentAtFireTwo)

        assertFalse(basicCity.getBuilding(fireNodes[0]).isBurning)
        assertFalse(basicCity.getBuilding(fireNodes[1]).isBurning)
    }
}
