package org.interview.appian.vigiles.api

import org.interview.appian.vigiles.api.exceptions.FireproofBuildingException
import org.interview.appian.vigiles.api.exceptions.NoFireFoundException

interface Building {

    /**
     * Get the location of this building
     *
     * @return [CityNode] representing the location
     */
    val location: CityNode

    /**
     * Find out if the building is fireproof
     *
     * @return true if the building is fireproof, otherwise false
     */
    val isFireproof: Boolean

    /**
     * Find out if the building is currently on fire
     *
     * @return true if the building is burning, otherwise false
     */
    var isBurning: Boolean

    /**
     * Extinguish the fire in the building
     *
     * @throws NoFireFoundException if the building is not on fire
     */
    @Throws(NoFireFoundException::class)
    fun extinguishFire()

    /**
     * Sets the building on fire. This method should only be used to set up the scenario.
     *
     * @throws FireproofBuildingException if the building is fireproof
     */
    @Throws(FireproofBuildingException::class)
    fun setFire()
}
