package com.neelhpatel.javajokelib;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JokesContent {

    private List<String> jokes;
    private Random random;

    public JokesContent() {
        random = new Random();
        jokes = Arrays.asList(
                "What happens to a frog's car when it breaks down?\nIt gets toad away.",
                "Why was six scared of seven?\nBecause seven \"ate\" nine.",
                "How do astronomers organize a party?\nThey planet.",
                "Want to hear a Potassium joke?\nK.",
                "A photon walks into a hotel. The desk clerk says, \"Welcome to our hotel. Can we help you with your luggage?\"\nThe photon says, \"No thanks, I'm traveling light.\"",
                "What did the 30 degree angle say to the 90 degree angle?\n\"You think you're always right!\"",
                "Wife: \"I look fat. Can you give me a compliment?\"\nHusband: \"You have perfect eyesight.\"",
                "I changed my password to \"incorrect\". So whenever I forget what it is, the computer will say \"Your password is incorrect\".",
                "When an employment application asks who is to be notified in case of emergency, I always write, \"A very good doctor\".",
                "If you think nobody cares whether you're alive, try missing a couple of payments.",
                "I hate when I am about to hug someone really sexy and my face hits the mirror.",
                "Did you hear about the guy whose whole left side was cut off?\nHe's all right now.",
                "Two atoms are walking down the street. One says, \"Oh no! I lost an electron!\", The other asks him, \"Are you sure?\", The first one says, \"Yeah, I'm positive\"",
                "What time did the man go to the dentist?\nTooth hurt-y.",
                "Astronomers got tired of watching the moon go round the earth for 24 hours, so they decided to call it a day.",
                "What do you call a fish with no eyes?\nFsh.",
                "What did the green grape say to the purple grape?\nBreathe, you fool, breathe!",
                "I'm reading a book about anti-gravity.\nIt's impossible to put down.",
                "I wondered why the baseball was getting bigger.\nThen it hit me.",
                "I used to be a banker, but I lost interest.",
                "Why do seagulls fly over the sea?\nBecause they aren't bay-gulls!",
                "Why was the javascript developer sad?\nBecause he didn't Node how to Express himself.",
                "How did the hipster burn the roof of his mouth?\nHe ate the pizza before it was cool.",
                "How did Darth Vader know what Luke was getting for Christmas?\nHe felt his presents.",
                "What's red and bad for your teeth?\nA Brick.",
                "What's orange and sounds like a parrot?\nA Carrot.",
                "How many tickles does it take to tickle an octopus?\nTen-tickles!",
                "At the rate law schools are turning them out, by 2050 there will be more lawyers than humans.",
                "Why did the turkey cross the road?\nTo prove he isn't a chicken.",
                "Why was 6 afraid of 7?\nBecause 7 8 9.",
                "A mom picks her son from school\nMom: What did you learn at school today?\nSon: Apparently not enough, I have to go back tomorrow.",
                "I introduced my girlfriend to my family today\nThe kids really liked her, but my wife seemed mad.",
                "Why can't atheists solve exponential problems?\nBecause they don't believe in higher powers.",
                "Mike, do you think I'm a bad mother?\nMy name is Paul.",
                "When I see lovers' names carved in a tree, I don't think it's sweet. I just think it's surprising how many people bring a knife on a date.",
                "Why did the shark keep swimming in circles?\nIt had a nosebleed.",
                "Pessimist: Things just can't get any worse!\nOptimist: Nah, of course they can!",
                "What goes up and down, but never moves?\nThe stairs.",
                "A family of mice were surprised by a big cat. Father Mouse jumped and and said, \"Bow-wow!\" The cat ran away.\n \"What was that, Father?\" asked Baby Mouse. \n\"Well, son, that's why it's important to learn a second language.\"",
                "Patient: Doctor, I have a pain in my eye whenever I drink tea.\nDoctor: Take the spoon out of the mug before you drink.",
                "Mother: \"Did you enjoy your first day at school?\"\nGirl: \"First day? Do you mean I have to go back tomorrow?",
                "What did the hot-dog say when he needed to use the bathroom?\nMust-Turd",
                "What kind of bagel can fly?\nA Plain Bagel.",
                "Where do animals go when their tails fall off?\nA retail store ;)",
                "What was Forrest Gump's password?\n1Forrest1.");
    }

    public String getRandomJoke(){
        return jokes.get( random.nextInt(jokes.size()));
    }

}
