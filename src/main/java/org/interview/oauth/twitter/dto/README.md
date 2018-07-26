This package holds mutable DTOs.

There is mainly one reason to use them: to distinct immutable value objects from it's mutable
data-representations, marked with @SerializedName annotations. It's generally a good idea not to mix
transport-related stuff and VO, when possible.

It's also (arguably) easier to create DTO from json using default adapter + annotations and then
map to VO, then write specific type adapters for parser to create VOs. Not to mention the fact
that annotations are easier to change then type adapters.