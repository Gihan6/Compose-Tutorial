package com.gihan.composetutorial

import com.gihan.composetutorial.gyms.domain.Gym

fun getDummyGymsData(): List<Gym> {

    return listOf(
        Gym(1, "gym1", "Description of gym 1", favourite = false, isOpen = true),
        Gym(2, "gym2", "Description of gym 2", favourite = false, isOpen = false)
    )

}
fun getDummyGym():Gym{
    return  Gym(1, "gym1", "Description of gym 1", favourite = false, isOpen = true)
}