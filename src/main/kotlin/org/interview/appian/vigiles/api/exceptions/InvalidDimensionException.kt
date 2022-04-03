package org.interview.appian.vigiles.api.exceptions

class InvalidDimensionException(invalidDimension: Int) :
    RuntimeException("Invalid dimension for a city: $invalidDimension")
