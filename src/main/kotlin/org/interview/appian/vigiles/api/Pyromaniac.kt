package org.interview.appian.vigiles.api

import org.interview.appian.vigiles.api.exceptions.FireproofBuildingException

/**
 * Utility class to set fires in a city
 */
object Pyromaniac {

    /**
     * Sets a number of fires {@param numFires} at {@param victimLocations} in the given {@param victimCity}
     *
     * @param victimCity City to be set on fire
     * @param victimLocations Locations to be set on fire
     * @throws FireproofBuildingException if one of the buildings in question is fireproof
     */
    @Throws(FireproofBuildingException::class)
    fun setFires(victimCity: City, victimLocations: Array<CityNode>) {
        for (location in victimLocations) {
            setFire(victimCity, location)
        }
    }

    /**
     * Sets a fire at the {@param location} in the given {@param victimCity}
     * @param victimCity City to be set on fire
     * @param location Location to be set on fire
     * @throws FireproofBuildingException if the building in question is fireproof
     */
    @Throws(FireproofBuildingException::class)
    fun setFire(victimCity: City, location: CityNode) {
        victimCity.getBuilding(location).setFire()
    }
}
