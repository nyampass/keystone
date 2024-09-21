(ns keystone.system
  (:require [integrant.core :as ig]
            [keystone.application.usecase]
            [keystone.infrastructure.repository]
            [keystone.infrastructure.adapter.phaser]
            [keystone.presentation.scenes.boot]
            [keystone.presentation.scenes.main]))

(def config
  {:presentation.scenes/boot {:next :main}
   :presentation.scenes/main {:usecases {:storyline (ig/ref :usecase/storyline)}}

   :usecase/storyline {}

   :infrastructure.repository/memory {}

   :infrastructure.adapter/phaser {:scenes [(ig/ref :presentation.scenes/boot)
                                            (ig/ref :presentation.scenes/main)]
                                   :width 1600 :height 1200}})

(defn init []
  (ig/init config))

