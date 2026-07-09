import { useEffect, useState } from "react";
import "./VehiculoForm.css";

function VehiculoForm({ onSubmit, vehiculo }) {

    const initialForm = {
        patente: "",
        marca: "",
        modelo: "",
        nombre: "",
        anio: "",
        kilometrajeActual: "",
        estado: ""
    };

    const [form, setForm] = useState(initialForm);

    const [errores, setErrores] = useState({});

    useEffect(() => {

        if (vehiculo) {
            setForm(vehiculo);
        } else {
            setForm(initialForm);
        }

        setErrores({});

    }, [vehiculo]);

    const handleChange = (e) => {

        const { name, value } = e.target;

        setForm({
            ...form,
            [name]: value
        });

        setErrores({
            ...errores,
            [name]: ""
        });

    };

    const validar = () => {

        const errores = {};

        const anioActual = new Date().getFullYear();

        const regexPatente = /^[A-Z]{4}-\d{2}$/;

        if (!form.patente.trim()) {
            errores.patente = "La patente es obligatoria.";
        } else if (!regexPatente.test(form.patente.toUpperCase())) {
            errores.patente = "Formato válido: KBCT-11";
        }

        if (!form.marca.trim()) {
            errores.marca = "La marca es obligatoria.";
        }

        if (!form.modelo.trim()) {
            errores.modelo = "El modelo es obligatorio.";
        }

        if (!form.nombre.trim()) {
            errores.nombre = "El nombre es obligatorio.";
        }

        if (!form.anio) {
            errores.anio = "Ingrese el año.";
        } else if (
            Number(form.anio) < 1980 ||
            Number(form.anio) > anioActual + 1
        ) {
            errores.anio = `Debe estar entre 1980 y ${anioActual + 1}.`;
        }

        if (form.kilometrajeActual === "") {
            errores.kilometrajeActual = "Ingrese el kilometraje.";
        } else if (Number(form.kilometrajeActual) < 0) {
            errores.kilometrajeActual = "No puede ser negativo.";
        }

        if (!form.estado) {
            errores.estado = "Seleccione un estado.";
        }

        setErrores(errores);

        return Object.keys(errores).length === 0;

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        if (!validar()) return;

        onSubmit({
            ...form,
            patente: form.patente.toUpperCase()
        });

    };

    return (

        <form className="vehiculo-form" onSubmit={handleSubmit}>

            <div>

                <input
                    name="patente"
                    placeholder="Patente"
                    maxLength={10}
                    value={form.patente}
                    onChange={handleChange}
                />

                {errores.patente &&
                    <span className="error">{errores.patente}</span>
                }

            </div>

            <div>

                <input
                    name="marca"
                    placeholder="Marca"
                    maxLength={50}
                    value={form.marca}
                    onChange={handleChange}
                />

                {errores.marca &&
                    <span className="error">{errores.marca}</span>
                }

            </div>

            <div>

                <input
                    name="modelo"
                    placeholder="Modelo"
                    maxLength={50}
                    value={form.modelo}
                    onChange={handleChange}
                />

                {errores.modelo &&
                    <span className="error">{errores.modelo}</span>
                }

            </div>

            <div>

                <input
                    name="nombre"
                    placeholder="Nombre"
                    maxLength={100}
                    value={form.nombre}
                    onChange={handleChange}
                />

                {errores.nombre &&
                    <span className="error">{errores.nombre}</span>
                }

            </div>

            <div>

                <input
                    type="number"
                    name="anio"
                    placeholder="Año"
                    min="1980"
                    max={new Date().getFullYear() + 1}
                    value={form.anio}
                    onChange={handleChange}
                />

                {errores.anio &&
                    <span className="error">{errores.anio}</span>
                }

            </div>

            <div>

                <input
                    type="number"
                    name="kilometrajeActual"
                    placeholder="Kilometraje"
                    min="0"
                    value={form.kilometrajeActual}
                    onChange={handleChange}
                />

                {errores.kilometrajeActual &&
                    <span className="error">{errores.kilometrajeActual}</span>
                }

            </div>

            <div>

                <select
                    name="estado"
                    value={form.estado}
                    onChange={handleChange}
                >

                    <option value="">Seleccione estado</option>

                    <option value="OPERATIVO">
                        Operativo
                    </option>

                    <option value="EN_MANTENCION">
                        En Mantención
                    </option>

                    <option value="FUERA_DE_SERVICIO">
                        Fuera de Servicio
                    </option>

                </select>

                {errores.estado &&
                    <span className="error">{errores.estado}</span>
                }

            </div>

            <button type="submit">

                {vehiculo ? "Actualizar" : "Guardar"}

            </button>

        </form>

    );

}

export default VehiculoForm;