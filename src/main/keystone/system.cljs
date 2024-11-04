(ns keystone.system
  (:require [integrant.core :as ig]
            [cljs.spec.alpha :as s]
            [keystone.frameworks.phaser]
            [keystone.models.usecase]
            [keystone.scenes.boot]
            [keystone.scenes.main]))

(def config
  {:scenes/boot {:next :main
                 :assets {:tilemaps [:episode1]
                          :images [:the_japan_collection_overgrown_backstreets
                                   :spritesheet_32x32]
                          :spritesheets {:items {:name "spritesheet_32x32.png"
                                                 :size [32 32]}
                                         :nerd {:size [180 150]}
                                         :player {:size [180 150]}}}}

   :scenes/main
   {:tilemap {:name :episode1 :width 64 :height 64}
    :tilesets [:the_japan_collection_overgrown_backstreets
               :spritesheet_32x32]}

   :usecase/storyline {}

   :frameworks/phaser
   {:scenes [(ig/ref :scenes/boot)
             (ig/ref :scenes/main)]
    :width 1600 :height 1200}})

(defn init []
  (s/check-asserts true)
  (ig/init config))
