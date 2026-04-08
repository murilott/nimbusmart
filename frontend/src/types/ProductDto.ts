import type { Tag } from "./Tag"

export type ProductDto = {
    id: number | null,
    name: string,
    description: string,
    image: string,
    tags: Tag
}