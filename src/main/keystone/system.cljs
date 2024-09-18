(ns keystone.system
  (:require [integrant.core :as ig]
            [keystone.adapter.phaser]
            [keystone.presentation.scenes.boot]
            [keystone.presentation.scenes.main]))

(def config
  {:usecase/storyline {}
   :usecase/script {:storage (ig/ref :repository/storage)}

   :repository/storage {}

   :adapter/phaser {:scenes [(ig/ref :presentation.scenes/boot)
                             (ig/ref :presentation.scenes/main)]
                    :width 1600 :height 1200}

   :presentation.scenes/boot {:next :main}
   :presentation.scenes/main {}})

(defn init []
  (ig/init config))