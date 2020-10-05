# Dogs Should Vote
## Cuvva test app

So:

1) I know this is overly-engineered maybe to the point of ridicule for such an app, but what I wanted to put across is that I care about scalability. So I'd be able to scale this app in terms of data structure and size, processing layers and new UI functionality quite easily in basically any direction. Or even in terms of platform, why not.
2) I'm also about leaning as much as possible on unit tests. UI test are important (though I have none here, see point 3), but through the choice of architecture (and UI data abstractions) it's possible to push so much of the logic in platform-agnostic code and leave the render layer so dumb that unit tests become at least as powerful as Robolectric tests.
3) Jetpack compose, because I think it's neat :) Or rather, it will be once it's matured in various production environments. It also opens up new interesting questions about what the tree structure of an app's UI can be and how state relates to that. The tools for UI testing Compose are a bit too limited at the moment, so I didn't bother. Also insisting on it made the app turn out quite ugly but, I'm still getting the hang of it.
4) The architecture is MVI-ish in spirit but not in structure. I don't have any particular excuses for not using more conventional reducers and intents other than this is the style I've arrived at personally partially out of conciseness, partly out of prefference.
5) I'm using some dodgy AndroidStudio version and build tools at the moment to enable Compose and Hilt and whatnot, so I dropped a debug apk in root in case you're having trouble building locally.

https://youtu.be/jt40yL31mdc?t=182 :*
