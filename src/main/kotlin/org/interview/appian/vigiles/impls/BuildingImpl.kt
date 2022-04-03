package org.interview.appian.vigiles.impls

import org.interview.appian.vigiles.api.Building
import org.interview.appian.vigiles.api.CityNode
import org.interview.appian.vigiles.api.exceptions.FireproofBuildingException
import org.interview.appian.vigiles.api.exceptions.NoFireFoundException

open class BuildingImpl(
    override val location: CityNode,
    override val isFireproof: Boolean = false,
    override var isBurning: Boolean = false,
) : Building {

    @Throws(NoFireFoundException::class)
    override fun extinguishFire() {
        if (isBurning) {
            isBurning = false
        } else {
            throw NoFireFoundException()
        }
    }

    @Throws(FireproofBuildingException::class)
    override fun setFire() {
        if (!isFireproof) {
            isBurning = true
        } else {
            throw FireproofBuildingException()
        }
    }
}
