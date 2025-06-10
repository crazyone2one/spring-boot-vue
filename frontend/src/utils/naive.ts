import {
    // create naive ui
    create,
    // component
    NButton,
    NForm,
    NFormItem,
    NH3,
    NIcon,
    NInput, NDataTable, NGrid, NGridItem, NCard, NSwitch
} from "naive-ui";
import type {App} from "vue";

const naive = create({
    components: [NButton, NForm, NFormItem, NH3, NIcon, NInput, NDataTable, NGrid, NGridItem, NCard, NSwitch],
});

export const installNaive = (app: App) => {
    app.use(naive);
};
