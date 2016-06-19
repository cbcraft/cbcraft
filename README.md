[![License](https://img.shields.io/badge/License-LGPLv3-green.svg)](https://raw.githubusercontent.com/cbcraft/cbcraft/master/LICENSE)
[![Latest Release](https://img.shields.io/github/release/cbcraft/cbcraft.svg?label=Latest Release)](https://github.com/cbcraft/cbcraft/releases/latest)

# CBCraft - Code, Block and Craft

## Index

* [About](#about)
* [License](https://raw.githubusercontent.com/cbcraft/cbcraft/master/LICENSE)
* [Downloads](https://github.com/cbcraft/cbcraft/releases)
* [Installation](#installation)
* [User Guide](https://github.com/cbcraft/cbcraft/wiki)
* [Issues](#issues)
* [Building](#building)
* [Contribution](#contribution)
* [Localization](#localization)
* [Credits](#credits)

## About

A block language with the objective to bring some curiosity about programming to young people.

## Installation

You install this mod by putting it into the `minecraft/mods/` folder. It has no additional hard dependencies.

## Issues

Crashing, have a suggestion, found a bug? Create an issue now!

1. Make sure your issue has not already been answered or fixed and you are using the latest version. Also think about whether your issue is a valid one before submitting it.
2. Go to [the issues page](https://github.com/cbcraft/cbcraft/issues) and click [new issue](https://github.com/cbcraft/cbcraft/issues/new)
3. Enter your a title of your issue (something that summarizes your issue), and then create a detailed description of the issue.
    * Do not tag it with something like `[Feature]` or `[Bug]`. When it is applicable, we will take care of it.
    * The following details are required. Not including them can cause the issue to be closed.
        * Forge version
        * CBCraft version
        * Crash log, when reporting a crash (Please make sure to use [pastebin](http://pastebin.com/))
            * Do not post an excerpt of what you consider important, instead:
            * Post the full log
        * Other mods and their version, when reporting an issue between CBCraft and another mod
            * Also consider updating these before submitting a new issue, it might be already fixed
        * A detailed description of the bug or feature
    * To further help in resolving your issues please try to include the follow if applicable:
        * What was expected?
        * How to reproduce the problem?
            * This is usually a great detail and allows us to fix it way faster
        * Server or Single Player?
        * Screen shots or Pictures of the problem
5. Click `Submit New Issue`, and wait for feedback!

Providing as many details as possible does help us to find and resolve the issue faster and also you getting a fixed version as fast as possible.

## Building

1. Clone this repository via 
  - SSH `git clone git@github.com:cbcraft/cbcraft.git` or 
  - HTTPS `git clone https://github.com/cbcraft/cbcraft.git`
2. Setup workspace 
  - Decompiled source `gradlew setupDecompWorkspace`
  - Obfuscated source `gradlew setupDevWorkspace`
3. Build `gradlew build`. Jar will be in `build/libs`
4. For developer: Setup IDE
  - IntelliJ: Import into IDE and execute `gradlew genIntellijRuns` afterwards
  - Eclipse: execute `gradlew eclipse`

## Contribution

Before you want to add major changes, you might want to discuss them with us first, before wasting your time.
If you are still willing to contribute to this project, you can contribute via [Pull-Request](https://help.github.com/articles/creating-a-pull-request).

Here are a few things to keep in mind that will help get your PR approved.

* A PR should be focused on content. Any PRs where the changes are only syntax will be rejected.
* Use the file you are editing as a style guide.
* Consider your feature.
  - Is your suggestion already possible using Vanilla + CBCraft?
  - Make sure your feature isn't already in the works, or hasn't been rejected previously.
  - If your feature can be done by any popular mod, discuss with us first.

Getting Started

1. Fork this repository
2. Clone the fork via
  * SSH `git clone git@github.com:<your username>/cbcraft.git` or 
  * HTTPS `git clone https://github.com/<your username>/cbcraft.git`
3. Change code base
4. Add changes to git `git add -A`
5. Commit changes to your clone `git commit -m "<summery of made changes>"`
6. Push to your fork `git push`
7. Create a Pull-Request on GitHub
8. Wait for review
9. Squash commits for cleaner history

If you are only doing single file pull requests, GitHub supports using a quick way without the need of cloning your fork. Also read up about [synching](https://help.github.com/articles/syncing-a-fork) if you plan to contribute on regular basis.

## Localization

### English Text

`en_US` is included in this repository, fixes to typos are welcome.

### Encoding

Files must be encoded as UTF-8.

### New Translations

You can provide any additional languages by creating a new file with the [appropriate language code](http://download1.parallels.com/SiteBuilder/Windows/docs/3.2/en_US/sitebulder-3.2-win-sdk-localization-pack-creation-guide/30801.htm).

Thanks to everyone helping out to improve localization of CBCraft.

## Credits

Thanks to
 
* Notch et al for Minecraft
* LexManos et al for MinecraftForge
* GoldSpy98 and bernas-antunes97 for CBCraft
* All [contributors](https://github.com/cbcraft/cbcraft/graphs/contributors)
