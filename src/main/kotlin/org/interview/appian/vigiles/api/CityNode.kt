package org.interview.appian.vigiles.api

/**
 * Represents a location in the city
 * Build a [CityNode] given coordinates
 *
 * @param x Coordinate on x axis.
 * @param y Coordinate on y axis.
 */
data class CityNode(
    /**
     * Get the X coordinate of this node
     *
     * @return the X coordinate of this node
     */
    val x: Int,
    /**
     * Get the Y coordinate of this node
     *
     * @return the Y coordinate of this node
     */
    val y: Int
)
