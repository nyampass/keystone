(ns keystone.domain.stolines
  (:require [cljs.spec.alpha :as s]
            [keystone.domain.episode :as episode]))

(s/def ::indexes (s/map-of string? string?))