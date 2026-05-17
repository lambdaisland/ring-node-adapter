# lambdaisland/ring-node-adapter

<!-- badges -->
[![cljdoc badge](https://cljdoc.org/badge/com.lambdaisland/ring-node-adapter)](https://cljdoc.org/d/com.lambdaisland/ring-node-adapter) [![Clojars Project](https://img.shields.io/clojars/v/com.lambdaisland/ring-node-adapter.svg)](https://clojars.org/com.lambdaisland/ring-node-adapter) ![](https://img.shields.io/clojars/dt/com.lambdaisland%2Fring-node-adapter?style=flat-square)
<!-- /badges -->

Ring adapter for Node.js

## Features

- Create web applications for Node.js in ClojureScript
- Follow the Ring spec, but adjust to what makes sense on Node
- Support async handlers through async functions/promises
- Dependency-free, just Node's built-in HTTP modules

<!-- installation -->
## Installation

To use the latest release, add the following to your `deps.edn` ([Clojure CLI](https://clojure.org/guides/deps_and_cli))

```clj
com.lambdaisland/ring-node-adapter {:mvn/version "0.1.5"}
```

or add the following to your `project.clj` ([Leiningen](https://leiningen.org/))

```clj
[com.lambdaisland/ring-node-adapter "0.1.5"]
```
<!-- /installation -->

## Rationale

This is a fairly obvious and trivial idea, but looking around I couldn't find
anyone who had done just this, a simple, minimal Ring-for-Node. The main prior
art to acknowledge is [Macchiato](https://macchiato-framework.github.io/),
however Macchiato purports to be an entire framework, not just a Ring adapter.

## Usage

```clj
(require 'lambdaisland.ring-node-adapter)

(lambdaisland.ring-node-adapter/run-adapter
  (fn [req]
    {:status 200 :body "OK!"})
  {:port 1234})
```

<!-- opencollective -->
## Lambda Island Open Source

Thank you! ring-node-adapter is made possible thanks to our generous backers. [Become a
backer on OpenCollective](https://opencollective.com/lambda-island) so that we
can continue to make ring-node-adapter better.

<a href="https://opencollective.com/lambda-island">
<img src="https://opencollective.com/lambda-island/organizations.svg?avatarHeight=46&width=800&button=false">
<img src="https://opencollective.com/lambda-island/individuals.svg?avatarHeight=46&width=800&button=false">
</a>
<img align="left" src="https://github.com/lambdaisland/open-source/raw/master/artwork/lighthouse_readme.png">

&nbsp;

ring-node-adapter is part of a growing collection of quality Clojure libraries created and maintained
by the fine folks at [Gaiwan](https://gaiwan.co).

Pay it forward by [becoming a backer on our OpenCollective](http://opencollective.com/lambda-island),
so that we continue to enjoy a thriving Clojure ecosystem.

You can find an overview of all our different projects at [lambdaisland/open-source](https://github.com/lambdaisland/open-source).

&nbsp;

&nbsp;
<!-- /opencollective -->

<!-- contributing -->
## Contributing

We warmly welcome patches to ring-node-adapter. Please keep in mind the following:

- adhere to the [LambdaIsland Clojure Style Guide](https://nextjournal.com/lambdaisland/clojure-style-guide)
- write patches that solve a problem 
- start by stating the problem, then supply a minimal solution `*`
- by contributing you agree to license your contributions as MPL 2.0
- don't break the contract with downstream consumers `**`
- don't break the tests

We would very much appreciate it if you also

- update the CHANGELOG and README
- add tests for new functionality

We recommend opening an issue first, before opening a pull request. That way we
can make sure we agree what the problem is, and discuss how best to solve it.
This is especially true if you add new dependencies, or significantly increase
the API surface. In cases like these we need to decide if these changes are in
line with the project's goals.

`*` This goes for features too, a feature needs to solve a problem. State the problem it solves first, only then move on to solving it.

`**` Projects that have a version that starts with `0.` may still see breaking changes, although we also consider the level of community adoption. The more widespread a project is, the less likely we're willing to introduce breakage. See [LambdaIsland-flavored Versioning](https://github.com/lambdaisland/open-source#lambdaisland-flavored-versioning) for more info.
<!-- /contributing -->

<!-- license -->
## License

Copyright &copy; 2026 Arne Brasseur and Contributors

Licensed under the term of the Mozilla Public License 2.0, see LICENSE.
<!-- /license -->
