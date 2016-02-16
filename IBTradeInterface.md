# CEP Trader IB Interface #

## Details ##

Current CEP trader code base takes IB data and has it pumped into Drools through POJOs. These POJOs are transformed into generic event POJOs within Drools. The transformed POJOs are the generic representation form the basis for further computation. Order events POJOs are subsequently transformed back to IB format to be executed.