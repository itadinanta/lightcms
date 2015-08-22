<p>Here I am again, in the third incarnation of Itadinanta. This time, I wrote
the engine in Java, leveraging Jetty and Spring. Will I ever quit
reinventing the wheel every single time? Well, actually, I tried, and not just once, but 
I am never happy about what I find "off-the-shelf"</p>

<p>I wrote the first version in PHP, leveraging my (d'oh!) mini-framework 
"elib", which I wrote once upon a time to build the 
<@nav.link url="http://www.dotsrl.com"/> and <@nav.link url="http://www.radixmalorum.it"/> websites. 
That solution was far from perfect, given that:
</p>

<ul>
	<li>Finding supporting developers was a mess. Although written in PHP, it required a quite deep knowledge of the language and the library,
	not to mention all the naughty stuff I wrote.
	</li>
	<li>The site was drowning in a sea of prerequisites,
	including system libraries, MySQL, Apache configuration files, 
	PHP extensions, and this made it very difficult to port
	across platform, or even across Linux distros.
	</li>
	<li>Development methodology was rather "crafty": Xemacs, Galeon, 
	a debugger-less debugging system, home-made remote logging, 
	no unit testing, no IDE.</li>
	<li>Any Other Business</li>
</ul>

<p>However, it was working. At least until after a catastrophic 
hardware crash featuring a BIOS undetectable hard drive, when I found myself reinstalling everything from scratch.
Just switching from Woody (the dead server) to Sarge (new install) left me with no working sites.</p>