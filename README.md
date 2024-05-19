simple cljs app that fetches images from picsum api. no finite state machine library used. This app follows event driven methodology.
### Development mode
```
npm install
npx shadow-cljs watch app
```
start a ClojureScript REPL
```
npx shadow-cljs browser-repl
```
### Building for production

```
npx shadow-cljs release app
```
