(ns keystone.application.usecase
  (:require [integrant.core :as ig]
            [keystone.application.usecase.storyline :as storyline]))

(defmethod ig/init-key :usecase/storyline [_ opts]
  (storyline/gen-usecase opts))