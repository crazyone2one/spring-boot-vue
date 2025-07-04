import {
    // create naive ui
    create,
    // component
    NButton,
    NForm,
    NFormItem,
    NH3,
    NIcon,
    NInput, NDataTable, NGrid, NGridItem, NCard, NSwitch, NFlex, NSelect, NSplit, NTree, NSpin, NSpace
} from "naive-ui";
import type {App} from "vue";

const naive = create({
    components: [NButton, NForm, NFormItem, NH3, NIcon, NInput, NDataTable, NGrid, NGridItem, NCard, NSwitch, NFlex,
        NFlex, NSelect, NSplit, NTree, NSpin, NSpace],
});

export const installNaive = (app: App) => {
    app.use(naive);
};
