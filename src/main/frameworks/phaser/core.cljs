(ns frameworks.phaser.core
  (:require [integrant.core :as ig]
            ["phaser" :as phaser]))

(defn gen-game [{:keys [debug? width height scenes] :or {debug? false}}]
  (let [config
        (clj->js {:type (.-WEBGL phaser)
                  :parent "root"
                  :width (.-innerWidth js/window)
                  :height (.-innerHeight js/window)
                  :pixelArt true
                  :physics {:default "arcade"
                            :arcade {:debug debug?}}
                  :scale {:mode (-> phaser .-Scale .-CENTER_BOTH),
                          :autoCenter (-> phaser .-Scale .-CENTER_BOTH),
                          :width width,
                          :height height},
                  :scene (clj->js scenes)})]
    (phaser/Game. config)))

(defmethod ig/init-key :frameworks/phaser [_ opts]
  (gen-game opts))
