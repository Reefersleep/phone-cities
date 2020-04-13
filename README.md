## Phone Cities - a score calculator for Lost Cities
[Lost Cities](https://boardgamegeek.com/boardgame/50/lost-cities) is a fun, swift card game for 2 players whose scoring at the end of the game can be a bit tedious. 

[Phone Cities](https://phone-cities.netlify.com) is a tool for helping you quickly tally up your score!

It's only really meant for use on a phone, in portrait mode, so if you try to use it on a laptop or on your phone in landscape mode, it'll probably act weird. Stay in portrait mode and you'll be fine!

Go try it now at [https://phone-cities.netlify.com](https://phone-cities.netlify.com)

### Heritage

Phone Cities inherits some of its design from its predecessor, [Fast Cities](https://fast-cities.netlify.com) (source code available [here](https://github.com/Reefersleep/fast-cities)). Fast Cities was also an attempt to make tallying scores for Lost Cities easier, using a visual design that mimics the actual game along with both easy point-and-click input as well swift keyboard input. Both the visual design and UX was clearly made with laptops in mind. That was great for me personally, but a friend of mine (who wanted to use it) remarked that most people who play board games don't have their laptop pulled up next to the game... Which was a good point, I had to admit. And thus, the mobile version was born!

## Development stuff
### Development mode with figwheel

To start the Figwheel compiler, navigate to the project folder and run the following command in the terminal:

```
lein figwheel
```

Figwheel will automatically push cljs changes to the browser.
Once Figwheel starts up, you should be able to open the `public/index.html` page in the browser. (Figwheel will also attempt to automatically open the app in a browser tab, so you may not need to do anything!)


### REPL

The project is setup to start nREPL on port `7002` once Figwheel starts.
Once you connect to the nREPL, run `(cljs)` to switch to the ClojureScript REPL.

### Building for production

```
lein clean
lein package
```
