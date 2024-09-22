(ns keystone.system
  (:require [integrant.core :as ig]
            [cljs.spec.alpha :as s]
            [keystone.application.usecase]
            [keystone.infrastructure.repository]
            [keystone.infrastructure.adapter.phaser]
            [keystone.presentation.scenes.boot]
            [keystone.presentation.scenes.main]))

(def config
  {:presentation.scenes/boot
   {:next :main
    :assets {:tilemaps [:episode1]
             :images [:the_japan_collection_overgrown_backstreets
                      :spritesheet_32x32]
             :spritesheets {:items {:name "spritsheet_32x32.png"}
                            :nerd {}
                            :player {}}}}
   :presentation.scenes/main {:usecases {:storyline (ig/ref :usecase/storyline)}}

   :usecase/storyline {}

   :infrastructure.repository/memory {}

   :infrastructure.adapter/phaser {:scenes [(ig/ref :presentation.scenes/boot)
                                            (ig/ref :presentation.scenes/main)]
                                   :width 1600 :height 1200}})

(defn init []
  (s/check-asserts true)
  (ig/init config))

