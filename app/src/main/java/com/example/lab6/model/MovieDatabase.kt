package com.example.lab6.model

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6.R

object MovieSDK : ViewModel() {

    //To simulate an actual movie database
    val movies = arrayListOf(
        Movie("Down", "Jackson gets a car for his girlfriend's 27th birthday. That night his girlfriend, Ballora, knocks him out and he wakes up in the car next to his girlfriend in the middle of the desert. When Jackson demands for an explaination and tries to stop the car, Ballora's face splits into pieces and instead of blood and gore it was a pile of machinery. \n\"I need to go...my sisters need me.\"\n\"Go where?\"\n\"Sisters' underground pizzaria, my rebirthplace.\"", 10.99, 5.99, R.raw.stock_footage_1),
        Movie("Elza in Winterworld", "As the clouds charge forward and all the animals fall into a deep slumber, our brave hero-slime, Elza, searches for a cure to save all of animal kind. She follows a rabbit into a hole and goes on wild and wacky adventures during her quest. Will Elza find out the cure? Find out in the next episo- wait I mixed up. Ahem. Find out in the new movie Elza in winterworld. This movie is sure to send chills down your spine, so get your tickets now lowly humans.", 13.7, 3.0, R.raw.stock_footage_2),
        Movie("Over the sun", "I was always told that a cow jumped over the moon, so what jumps over the sun? Our cloudy friend, Stanley, meets the goddess, Kamisama, that flew to the sun and proposes to her. Kamisama was hot but cool, her ice cold glare nearly caused Stanley to hail. A slice of life movie about a cloud's pursuit of love and the cold goddess of the sun.", 12.7, 5.46, R.raw.stock_footage_3),
        Movie("My sweet honey, your sour lime", "One agent goes undercover into a trillion dollar company, but two come out. Holly, codename \"Honey\", invaded 'Pear inc.' as her last mission before quiting her job for good. She thought it would be easy. Go in, dig out some secrets of their CEO and come out. She did not expect herself to be caught on the first day and met face to face with the CEO himself, Lenon Lime. \"Holly ,or should I call you Honey, make your choice. Die here or become our double agent.\" Holly knew this was going to be a hectic ride.", 20.43, 6.74, R.raw.stock_footage_4),
        Movie("God's realm", "I see it all. Every animal, every person in the world. \nA god has been born, the god of stuff. \"Wow that sounds boring, couldn't I have gotten a better title.\" Despite that due to how generic 'their' power was, the stuff 'they' control was almost everything in the world. However 'they' was immediately kicked out of the god's realm to create a name for 'themself' before 'they' was allowed back in. The god of stuff is the god of all. 'They' went around helping different parts of the world to save the people in exchange for a name. You would not believe in the name of god what 'their' name is!", 8.94, 4.44, R.raw.stock_footage_5),
        Movie("Aeroes", "Aeroes, the aviary heroes of the Flight and the Flightless alike and enemies of the Repro. This is a story of Linsey who was one of the young admirers of these flying heroes and another Flightless with a pipe dream. He hoped to soar to the skies even if he was just a minor character of the many that failed before him. He researched day after day, year after year, wandering aimlessly but with a singular goal in mind. His hardworked paid off too, as he created a pair of rocket boots never seen before. He rushes to attend the Aero admission test being the first and only flightless in history to pass. He faced many trials and tribulations before securing the spot at the top. Watch Linsey's guide to soar to the top of the sky now!", 9.99, 4.20, R.raw.stock_footage_6),
        Movie("Outerwater experience", "The frog in the well longs for the ground. The humans on the ground long for space. Then what about those in space? Our little Jello floating in the far away Hellas Basin decides to search for a better lifestyle for its future children. She meets a creature similar to an octopus called Arsula. Jello and Arsula became close until Arsula gave Jello a brilliant proposal. Jello ,who had full trust in Arsula, had taken for granted her ability to fly as she traded with him, trading her flight for an ability to swim. Arsula with his new gained powers flies away out of Mar's atmosphere leaving Jello on the surface without a single drop of water to swim in. Would Jello be able to survive this ordeal, I guess you will find out soon.", 6.90, 2.70, R.raw.stock_footage_7),
    )

    val cart = MutableLiveData(
        arrayListOf<Ticket>()
    )

    fun buy(movie: Movie, type: TicketType, copies: Int) {
        cart.value?.add(Ticket(
            movie,
            type,
            if (type == TicketType.ADULT_TICKET) movie.adultPrice * copies else movie.childPrice * copies,
            copies)
        )
        cart.value = cart.value
    }

    fun remove(ticket: Ticket): Boolean {
        val result = cart.value?.remove(ticket) ?: false
        cart.value = cart.value
        return result
    }

    fun totalPrice(): Double{
        var ans = 0.0
        for (t in cart.value ?: arrayListOf()) ans += t.cost
        return ans
    }

}

data class Movie (
    val name: String,
    val desc: String,
    val adultPrice: Double,
    val childPrice: Double,
    val trailer_id: Int
)

data class Ticket (
    val movie: Movie,
    val type: TicketType,
    val cost: Double,
    val copies: Int
)

enum class TicketType {
    CHILD_TICKET,
    ADULT_TICKET
}

fun getUriFromRaw(context: Context, id: Int): Uri {
    return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.packageName)
            .path(id.toString())
            .build()
}