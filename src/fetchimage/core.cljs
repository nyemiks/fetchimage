(ns fetchimage.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

;; -------------------------
;; Views

;https://picsum.photos/200/300
; https://picsumT.photos/200/300  error url
(defonce image-url "https://picsum.photos/200/300")


(def isFetching  (r/atom false))
 (def isError     (r/atom false))
 (def isSuccess   (r/atom false))
  ( def image        (r/atom nil) )


(defn fetch-images []
   (.info js/console "fetch image from: " image-url)
  
(-> (js/fetch image-url)
; (. then (fn [response] (.json response)))
    (. then (fn [response] (.blob response)))
(. then (fn [data]
          (let [
                url (.createObjectURL js/URL data)
                ]
            (.info js/console "image json response: " url)        
             (reset! image url )
             (reset! isSuccess true)
            )
          )
   )
(. catch (fn [e]
           (.info js/console  "error trace: " e)
            (reset! isError true)
           )
   )
    (. finally (fn []
                  (.info js/console "finally !! fetching set to false ")
            (reset! isFetching false)
           )
   )
    )
  )


(defn image-fetcher []
  [:section
        (when @isFetching [:p "loading..."])
        (when @isSuccess  [:div [:img {
                                   :src @image
                                   :alt ""
                                   :style {
                                           :height "150px"
                                           :width "150px"
                                           :border "solid gray 3px" 
                                           :border-radius "10px"
                                           }
                                   }
                             ]
                       ]
              )
        (when @isError [:p "An error occured"])
        [:button {
                  :on-click (fn []
                              (if (= @isFetching true) 
                               (do                                
                                 (.info js/console "image fetch already in progress. exiting...")
                                 nil
                                 )
                                (do 
                                  (reset! isFetching true)                             
                                   (fetch-images)  
                                  )
                                )
                                                        
                              )
                  } "Get Image"]
       ]
  )


(defn home-page []
  [:div 
     [:h2 "Welcome to image fetch (no fsm)"]
     [image-fetcher]
   ]
  
  )

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
