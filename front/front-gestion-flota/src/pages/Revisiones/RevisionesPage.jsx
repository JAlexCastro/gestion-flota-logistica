import { useEffect, useState } from "react";

import Modal from "../../components/Modal/Modal";

import RevisionTecnicaTable from "./RevisionTecnicaTable";
import RevisionTecnicaForm from "./RevisionTecnicaForm";

import EmisionGasesTable from "./EmisionGasesTable";
import EmisionGasesForm from "./EmisionGasesForm";

import {
    listarRevisiones,
    crearRevision,
    actualizarRevision,
    eliminarRevision
} from "../../services/revisionTecnicaService";

import {
    listarEmisiones,
    crearEmision,
    actualizarEmision,
    eliminarEmision
} from "../../services/emisionesService";

function RevisionesPage() {

    const [revisiones, setRevisiones] = useState([]);
    const [emisiones, setEmisiones] = useState([]);

    const [openRevision, setOpenRevision] = useState(false);
    const [openEmision, setOpenEmision] = useState(false);

    const [editRevision, setEditRevision] = useState(null);
    const [editEmision, setEditEmision] = useState(null);

    useEffect(() => {
        cargarTodo();
    }, []);

    const cargarTodo = async () => {

        const rev = await listarRevisiones();
        const emi = await listarEmisiones();

        setRevisiones(rev.data);
        setEmisiones(emi.data);

    };

    // REVISIONES

    const guardarRevision = async (data) => {

        if (editRevision) {
            await actualizarRevision(editRevision.id, data);
        } else {
            await crearRevision(data);
        }

        setOpenRevision(false);
        setEditRevision(null);

        cargarTodo();

    };

    // EMISIONES

    const guardarEmision = async (data) => {

        if (editEmision) {
            await actualizarEmision(editEmision.id, data);
        } else {
            await crearEmision(data);
        }

        setOpenEmision(false);
        setEditEmision(null);

        cargarTodo();

    };

    return (

        <div>

            {/* REVISION TECNICA */}

            <div className="page-header">

                <h1>Revisiones Técnicas</h1>

                <button
                    className="btn-primary"
                    onClick={() => {
                        setEditRevision(null);
                        setOpenRevision(true);
                    }}
                >
                    Nueva Revisión
                </button>

            </div>

            <RevisionTecnicaTable
                revisiones={revisiones}
                onEdit={(r) => {
                    setEditRevision(r);
                    setOpenRevision(true);
                }}
                onDelete={async (id) => {
                    await eliminarRevision(id);
                    cargarTodo();
                }}
            />

            {/* EMISIONES */}

            <div className="page-header">

                <h1>Emisiones de Gases</h1>

                <button
                    className="btn-primary"
                    onClick={() => {
                        setEditEmision(null);
                        setOpenEmision(true);
                    }}
                >
                    Nueva Emisión
                </button>

            </div>

            <EmisionGasesTable
                emisiones={emisiones}
                onEdit={(e) => {
                    setEditEmision(e);
                    setOpenEmision(true);
                }}
                onDelete={async (id) => {
                    await eliminarEmision(id);
                    cargarTodo();
                }}
            />

            {/* MODAL REVISION */}

            <Modal
                isOpen={openRevision}
                title={
                    editRevision
                        ? "Editar Revisión Técnica"
                        : "Nueva Revisión Técnica"
                }
                onClose={() => setOpenRevision(false)}
            >
                <RevisionTecnicaForm
                    revision={editRevision}
                    onSubmit={guardarRevision}
                />
            </Modal>

            {/* MODAL EMISION */}

            <Modal
                isOpen={openEmision}
                title={
                    editEmision
                        ? "Editar Emisión"
                        : "Nueva Emisión"
                }
                onClose={() => setOpenEmision(false)}
            >
                <EmisionGasesForm
                    emision={editEmision}
                    onSubmit={guardarEmision}
                />
            </Modal>

        </div>

    );
}

export default RevisionesPage;